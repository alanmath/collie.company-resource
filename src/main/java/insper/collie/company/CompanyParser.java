package insper.collie.company;

public class CompanyParser {
    
    public static Company to(CompanyInfo in) {
        return Company.builder()
            .name(in.name())
            .description(in.description())
            .build();
    }

    public static CompanyInfo to(Company company) {
        return CompanyInfo.builder()
            .name(company.name())
            .description(company.description())
            .build();
    }
}
