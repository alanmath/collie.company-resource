package insper.collie.company;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import insper.collie.company.exceptions.CompanyNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public Company create(Company in) {
        return companyRepository.save(new CompanyModel(in)).to();
    }

    @Transactional(readOnly = true)
    public Company getCompany(String id) {
        Optional<CompanyModel> company = companyRepository.findById(id);
        if (company.isPresent()){
            return company.get().to();
        }
        throw new CompanyNotFoundException(id);

    }

    @Transactional(readOnly = true)
    public Boolean isCompany(String id) {
        return companyRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<Company>();

        for (CompanyModel c : companyRepository.findAll()){
            companies.add(c.to());
        };

        return companies;
    }

    @Transactional
    public Company update(String id, Company in) {
        CompanyModel c = companyRepository.findById(id).orElse(null);
        if (c == null) throw new CompanyNotFoundException(id);


        CompanyModel company = c;

        if (in.name() != null) {
            company.name(in.name());
        }
        if(in.description() != null){
            company.description(in.description());
        }

        return companyRepository.save(company).to();
    }

    @Transactional
    public void delete(String id) {
        if (companyRepository.existsById(id)) companyRepository.deleteById(id);
        throw new CompanyNotFoundException(id);
    }

}
    

