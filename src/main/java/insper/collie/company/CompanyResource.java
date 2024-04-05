package insper.collie.company;

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

    
}
