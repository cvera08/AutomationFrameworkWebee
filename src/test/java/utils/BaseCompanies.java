package utils;

/**
 * Created by Carlos Vera on 09/12/2017.
 * This class contains base name for all the companies presents in your company.
 */
public class BaseCompanies {

    public static enum Companies {
        M3("3m"), HPE("hpe"), CRM("crm"), SPECTRA("spectra");
        private String value;

        private Companies(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
