package tech.provve.api.server.generated.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RobokassaConfirmPaymentRequest {

    private String outSum;

    private Integer invId;

    private BigDecimal fee;

    private String email;

    private String signatureValue;

    private String paymentMethod;

    private String incCurrLabel;

    private Map<String, String> shp = new HashMap<>();

    public RobokassaConfirmPaymentRequest() {

    }

    public RobokassaConfirmPaymentRequest(String outSum, Integer invId, BigDecimal fee, String email, String signatureValue, String paymentMethod, String incCurrLabel, Map<String, String> shp) {
        this.outSum = outSum;
        this.invId = invId;
        this.fee = fee;
        this.email = email;
        this.signatureValue = signatureValue;
        this.paymentMethod = paymentMethod;
        this.incCurrLabel = incCurrLabel;
        this.shp = shp;
    }


    @JsonProperty("OutSum")
    public String getOutSum() {
        return outSum;
    }

    public void setOutSum(String outSum) {
        this.outSum = outSum;
    }


    @JsonProperty("InvId")
    public Integer getInvId() {
        return invId;
    }

    public void setInvId(Integer invId) {
        this.invId = invId;
    }


    @JsonProperty("Fee")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }


    @JsonProperty("EMail")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @JsonProperty("SignatureValue")
    public String getSignatureValue() {
        return signatureValue;
    }

    public void setSignatureValue(String signatureValue) {
        this.signatureValue = signatureValue;
    }


    @JsonProperty("PaymentMethod")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    @JsonProperty("IncCurrLabel")
    public String getIncCurrLabel() {
        return incCurrLabel;
    }

    public void setIncCurrLabel(String incCurrLabel) {
        this.incCurrLabel = incCurrLabel;
    }


    @JsonProperty("Shp_")
    public Map<String, String> getShp() {
        return shp;
    }

    public void setShp(Map<String, String> shp) {
        this.shp = shp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RobokassaConfirmPaymentRequest robokassaConfirmPaymentRequest = (RobokassaConfirmPaymentRequest) o;
        return Objects.equals(outSum, robokassaConfirmPaymentRequest.outSum) &&
                Objects.equals(invId, robokassaConfirmPaymentRequest.invId) &&
                Objects.equals(fee, robokassaConfirmPaymentRequest.fee) &&
                Objects.equals(email, robokassaConfirmPaymentRequest.email) &&
                Objects.equals(signatureValue, robokassaConfirmPaymentRequest.signatureValue) &&
                Objects.equals(paymentMethod, robokassaConfirmPaymentRequest.paymentMethod) &&
                Objects.equals(incCurrLabel, robokassaConfirmPaymentRequest.incCurrLabel) &&
                Objects.equals(shp, robokassaConfirmPaymentRequest.shp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outSum, invId, fee, email, signatureValue, paymentMethod, incCurrLabel, shp);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RobokassaConfirmPaymentRequest {\n");

        sb.append("    outSum: ")
          .append(toIndentedString(outSum))
          .append("\n");
        sb.append("    invId: ")
          .append(toIndentedString(invId))
          .append("\n");
        sb.append("    fee: ")
          .append(toIndentedString(fee))
          .append("\n");
        sb.append("    email: ")
          .append(toIndentedString(email))
          .append("\n");
        sb.append("    signatureValue: ")
          .append(toIndentedString(signatureValue))
          .append("\n");
        sb.append("    paymentMethod: ")
          .append(toIndentedString(paymentMethod))
          .append("\n");
        sb.append("    incCurrLabel: ")
          .append(toIndentedString(incCurrLabel))
          .append("\n");
        sb.append("    shp: ")
          .append(toIndentedString(shp))
          .append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString()
                .replace("\n", "\n    ");
    }
}
