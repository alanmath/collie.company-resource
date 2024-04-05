package insper.collie.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    public Company create(Company in) {
        return companyRepository.save(new CompanyModel(in)).to();
    }

}
    

