package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import dev.java10x.CadastroDeNinjas.Missoes.Service.MissoesService;
import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Service.NinjaService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ninjas/ui")
@Hidden
public class NinjaControllerUI {

    private final NinjaService ninjaService;
    private final MissoesService missoesService;

    // Controller MVC usado pelo Thymeleaf.
    // Diferente do NinjaController REST, aqui os metodos retornam nomes de templates HTML.
    public NinjaControllerUI(NinjaService ninjaService, MissoesService missoesService) {
        this.ninjaService = ninjaService;
        this.missoesService = missoesService;
    }

    // GET /ninjas/ui/listar - carrega os dados do service e entrega para o template Thymeleaf.
    @GetMapping({ "", "/", "/listar" })
    public String listarNinjas(Model model) {
        List<NinjaDTO> ninjas = ninjaService.listarTodosNinjas();
        model.addAttribute("ninjas", ninjas);
        model.addAttribute("totalNinjas", ninjas.size());
        return "ninjas/listar";
    }

    // GET /ninjas/ui/detalhar/{id} - mostra um unico ninja em uma pagina de detalhes.
    @GetMapping("/detalhar/{id}")
    public String detalharNinja(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        NinjaDTO ninja = ninjaService.mostrarNinjasPorId(id);

        if (ninja == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Ninja nao encontrado.");
            return "redirect:/ninjas/ui/listar";
        }

        model.addAttribute("ninja", ninja);
        return "ninjas/detalhar";
    }

    // GET /ninjas/ui/criar - abre o formulario HTML com um DTO vazio para o Thymeleaf preencher.
    @GetMapping("/criar")
    public String formularioCriarNinja(Model model) {
        model.addAttribute("ninja", new NinjaDTO());
        model.addAttribute("missoes", missoesService.listarMissoes());
        model.addAttribute("modoEdicao", false);
        model.addAttribute("tituloFormulario", "Cadastrar ninja");
        model.addAttribute("textoBotao", "Salvar ninja");
        return "ninjas/formulario";
    }

    // POST /ninjas/ui/criar - recebe os campos do formulario, monta NinjaDTO e salva pelo service.
    @PostMapping("/criar")
    public String criarNinja(@ModelAttribute("ninja") NinjaDTO ninjaDTO, RedirectAttributes redirectAttributes) {
        NinjaDTO ninjaSalvo = ninjaService.criarNinja(ninjaDTO);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja " + ninjaSalvo.getNome() + " cadastrado com sucesso.");
        return "redirect:/ninjas/ui/listar";
    }

    // GET /ninjas/ui/editar/{id} - carrega o NinjaDTO existente e reutiliza o formulario em modo edicao.
    @GetMapping("/editar/{id}")
    public String formularioEditarNinja(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        NinjaDTO ninja = ninjaService.mostrarNinjasPorId(id);

        if (ninja == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Ninja nao encontrado para edicao.");
            return "redirect:/ninjas/ui/listar";
        }

        model.addAttribute("ninja", ninja);
        model.addAttribute("missoes", missoesService.listarMissoes());
        model.addAttribute("modoEdicao", true);
        model.addAttribute("tituloFormulario", "Editar ninja");
        model.addAttribute("textoBotao", "Atualizar ninja");
        return "ninjas/formulario";
    }

    // POST /ninjas/ui/editar/{id} - recebe o formulario editado e atualiza o registro existente.
    @PostMapping("/editar/{id}")
    public String atualizarNinja(@PathVariable Long id,
                                 @ModelAttribute("ninja") NinjaDTO ninjaDTO,
                                 RedirectAttributes redirectAttributes) {
        NinjaDTO ninjaAtualizado = ninjaService.alterarNinja(id, ninjaDTO);

        if (ninjaAtualizado == null) {
            redirectAttributes.addFlashAttribute("mensagem", "Ninja nao encontrado para atualizacao.");
            return "redirect:/ninjas/ui/listar";
        }

        redirectAttributes.addFlashAttribute("mensagem", "Ninja " + ninjaAtualizado.getNome() + " atualizado com sucesso.");
        return "redirect:/ninjas/ui/detalhar/" + ninjaAtualizado.getId();
    }

    // POST /ninjas/ui/deletar/{id} - HTML nao envia DELETE facilmente, entao usamos POST para acionar a remocao.
    @PostMapping("/deletar/{id}")
    public String deletarNinja(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ninjaService.deleteNinjaById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Ninja removido com sucesso.");
        return "redirect:/ninjas/ui/listar";
    }
}
