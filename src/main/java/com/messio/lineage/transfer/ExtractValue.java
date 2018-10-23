package com.messio.lineage.transfer;

import com.messio.lineage.domain.Company;
import com.messio.lineage.domain.Extract;

import java.util.List;

public class ExtractValue {
    private Extract extract;
    private List<Company> companies;

    public ExtractValue(Extract extract, List<Company> companies) {
        this.extract = extract;
        this.companies = companies;
    }

    public ExtractValue() {
    }

    public Extract getExtract() {
        return extract;
    }

    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}
