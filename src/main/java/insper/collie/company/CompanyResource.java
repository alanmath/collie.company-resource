package insper.collie.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@Tag(name = "Company", description = "API de Empresas")
public class CompanyResource implements CompanyController{
    
    @Autowired
    private CompanyService companyService;

    @Override
    @PostMapping("/companies")
    @Operation(summary = "Criar uma nova Empresa", description = "Cria uma nova empresa e retorna o objeto criado com seu ID.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso", content = @Content(schema = @Schema(implementation = CompanyInfo.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
    public ResponseEntity<CompanyInfo> create(CompanyInfo in) {

        Company company = CompanyParser.to(in);

        // insert using service
        company = companyService.create(company);
        // return
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(company.id())
                .toUri())
            .body(CompanyParser.to(company));
    }

    @Override
    @GetMapping("/companies/{id}")
    @Operation(summary = "Obter Empresa pelo ID", description = "Obtém as informações de uma empresa específica pelo seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada", content = @Content(schema = @Schema(implementation = CompanyInfo.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
        })
    public ResponseEntity<CompanyInfo> getCompany(String id){

        Company company = companyService.getCompany(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CompanyParser.to(company));
    }

    @Override
    @GetMapping("/companies/{id}/exists")
    @Operation(summary = "Verificar se a Empresa existe", description = "Verifica se uma empresa específica existe pelo seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "True se a empresa existe, False caso contrário")
        })
    public ResponseEntity<Boolean> isCompany(String id){

        Boolean isCompany = companyService.isCompany(id);

        return ResponseEntity.ok(isCompany);
    }

    @Override
    @GetMapping("/companies")
    @Operation(summary = "Listar todas as Empresas", description = "Lista todas as empresas cadastradas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas encontrada", content = @Content(schema = @Schema(implementation = CompanyInfo[].class))),
            @ApiResponse(responseCode = "404", description = "Nenhuma empresa encontrada")
        })
    public ResponseEntity<List<CompanyInfo>> getAllCompanies(){

        List<Company> companies = companyService.getAllCompanies();
        if (companies.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
            companies.stream()
                .map(CompanyParser::to)
                .collect(Collectors.toList())
        );
    }

    @Override
    @PutMapping("/companies/{id}")
    @Operation(summary = "Atualizar uma Empresa", description = "Atualiza as informações de uma empresa específica.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso", content = @Content(schema = @Schema(implementation = CompanyInfo.class))),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
    public ResponseEntity<CompanyInfo> updateCompany(String id, CompanyInfo in){

        Company company = CompanyParser.to(in);
        company = companyService.update(id, company);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CompanyParser.to(company));
    }

    @Override
    @DeleteMapping("/companies/{id}")
    @Operation(summary = "Deletar uma Empresa", description = "Deleta uma empresa específica pelo seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Empresa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
        })
    public ResponseEntity<CompanyInfo> deleteCompany(String id){

        String r = companyService.delete(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    
}
