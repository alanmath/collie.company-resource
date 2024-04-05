package insper.collie.company;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class CompanyResource implements CompanyController{
    
    @Autowired
    private CompanyService companyService;


    @Override
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
    public ResponseEntity<CompanyInfo> getCompany(String id){

        Company company = companyService.getCompany(id);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CompanyParser.to(company));
    }

    @Override
    public ResponseEntity<Boolean> isCompany(String id){

        Boolean isCompany = companyService.isCompany(id);

        return ResponseEntity.ok(isCompany);
    }

    @Override
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
    public ResponseEntity<CompanyInfo> updateCompany(String id, CompanyInfo in){

        Company company = CompanyParser.to(in);
        company = companyService.update(id, company);
        if (company == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CompanyParser.to(company));
    }

    @Override
    public ResponseEntity<CompanyInfo> deleteCompany(String id){

        String r = companyService.delete(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    
}
