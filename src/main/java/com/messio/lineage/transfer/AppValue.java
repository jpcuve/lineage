package com.messio.lineage.transfer;

import com.messio.lineage.domain.Company;
import com.messio.lineage.domain.Extract;

public class AppValue {
    private Extract extract;
    private Company one;
    private Company two;

    public AppValue(Extract extract, Company one, Company two) {
        this.extract = extract;
        this.one = one;
        this.two = two;
    }

    public AppValue() {
    }

    public Extract getExtract() {
        return extract;
    }

    public void setExtract(Extract extract) {
        this.extract = extract;
    }

    public Company getOne() {
        return one;
    }

    public void setOne(Company one) {
        this.one = one;
    }

    public Company getTwo() {
        return two;
    }

    public void setTwo(Company two) {
        this.two = two;
    }
}
