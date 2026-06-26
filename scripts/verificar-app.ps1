$porta = 8080
$nomeAplicacao = "dev.java10x.CadastroDeNinjas.CadastroDeNinjasApplication"

# Script de diagnostico para quando o IntelliJ para a tela, mas o Java continua vivo.
# Primeiro olhamos a porta 8080, porque e o sintoma mais comum quando a aplicacao nao sobe de novo.
Write-Host "Verificando porta $porta..." -ForegroundColor Cyan

$conexoes = netstat -ano | Select-String ":$porta"
$pidsDaPorta = @()

if (-not $conexoes) {
    Write-Host "Nenhum processo esta usando a porta $porta." -ForegroundColor Green
}
else {
    Write-Host "A porta $porta esta em uso:" -ForegroundColor Yellow
    $conexoes

    $pidsDaPorta = $conexoes |
        ForEach-Object { ($_ -split "\s+")[-1] } |
        Where-Object { $_ -match "^\d+$" -and $_ -ne "0" } |
        Select-Object -Unique

    Write-Host ""
    Write-Host "Processos encontrados pela porta:" -ForegroundColor Cyan
    $pidsDaPorta | ForEach-Object {
        Get-Process -Id $_ -ErrorAction SilentlyContinue |
            Select-Object Id, ProcessName, Path
    }
}

Write-Host ""
Write-Host "Verificando processos Java deste projeto..." -ForegroundColor Cyan

try {
    # Quando o Windows permite, filtramos pelo nome da classe main para nao confundir com o Java do IntelliJ.
    $processos = Get-CimInstance Win32_Process -Filter "name = 'java.exe'" -ErrorAction Stop |
        Where-Object { $_.CommandLine -like "*$nomeAplicacao*" } |
        Select-Object ProcessId, CommandLine
}
catch {
    # Em alguns terminais sem permissao elevada, o Windows bloqueia a leitura da linha de comando.
    # Nesse caso, a porta 8080 acima continua sendo o melhor indicador pratico.
    Write-Host "O Windows bloqueou a leitura detalhada dos processos Java." -ForegroundColor Yellow
    Write-Host "Use a lista acima pela porta $porta para saber se algo ficou preso." -ForegroundColor Yellow
    exit 0
}

if (-not $processos) {
    Write-Host "Nenhuma aplicacao CadastroDeNinjas esta rodando agora." -ForegroundColor Green
}
else {
    $processos | ForEach-Object {
        Write-Host "Aplicacao encontrada no PID $($_.ProcessId)." -ForegroundColor Yellow
        Write-Host $_.CommandLine
        Write-Host ""
    }
}
