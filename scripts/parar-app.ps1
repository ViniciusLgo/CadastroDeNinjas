$porta = 8080
$nomeAplicacao = "dev.java10x.CadastroDeNinjas.CadastroDeNinjasApplication"
$pidsParaEncerrar = @()

# Script para liberar o projeto quando a aplicacao fica presa depois do stop.
# A ideia e parar somente o Spring Boot deste projeto, sem encerrar o Java interno do IntelliJ.
Write-Host "Procurando processos da aplicacao CadastroDeNinjas..." -ForegroundColor Cyan

try {
    # Caminho mais seguro: identificar o processo pela classe main da aplicacao.
    $processos = Get-CimInstance Win32_Process -Filter "name = 'java.exe'" -ErrorAction Stop |
        Where-Object { $_.CommandLine -like "*$nomeAplicacao*" }

    $pidsParaEncerrar += $processos | ForEach-Object { $_.ProcessId }
}
catch {
    # Fallback para terminais em que o Windows bloqueia a linha de comando do processo.
    # Se a aplicacao esta travada, normalmente ela aparece ocupando a porta 8080.
    Write-Host "O Windows bloqueou a leitura detalhada dos processos Java." -ForegroundColor Yellow
    Write-Host "Vou usar a porta $porta para encontrar o processo preso." -ForegroundColor Yellow
}

# Mesmo quando a classe main nao aparece, a porta 8080 revela qual PID esta bloqueando o novo play.
$conexoes = netstat -ano | Select-String ":$porta"
if ($conexoes) {
    $pidsParaEncerrar += $conexoes |
        ForEach-Object { ($_ -split "\s+")[-1] } |
        Where-Object { $_ -match "^\d+$" -and $_ -ne "0" }
}

$pidsParaEncerrar = $pidsParaEncerrar | Select-Object -Unique

if (-not $pidsParaEncerrar) {
    Write-Host "Nenhuma aplicacao CadastroDeNinjas estava rodando." -ForegroundColor Green
    exit 0
}

$pidsParaEncerrar | ForEach-Object {
    Write-Host "Encerrando PID $_..." -ForegroundColor Yellow
    Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue
}

Write-Host "Aplicacao encerrada. Agora voce pode dar play novamente." -ForegroundColor Green
