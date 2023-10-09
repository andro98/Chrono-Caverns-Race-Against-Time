package com.aman.payment.maazoun.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONObject;

import com.aman.payment.maazoun.model.MaazounBookQuota;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO extends MaazounBookQuota {
    private static final long serialVersionUID = 1L;
    private String id;
    private String serialNumber;
    private String bookTypeName;
    private String bookTypeId;
    private String bookType;
    private String bookTierId;
    private String bookTierName;
    private String locationId;
    private String locationName;
    private String createAt;
    private String createBy;
    private String posId;
    private String posName;
    private String typeId;
    private String type;
    private String amanLogoUrl;
    private Double fees;
    private Long contractCount;
    private String maazounname;
    private String maazounId;
    private String maazounNationalId;
    private String bookFinancialNumber;
    private String status;
    private String isPrinted;
    private String contractNumber;
    private String sectorId;
    private String sectorName;
    private String contractFinancialNumber;
    private String supplyOrderId;
    private String maazounWarehouseId;
    private String collectionInfoId;
    private String receivedStatus;
    private String custody;
    private String maazouniaChurchNameType;
    private String bookInventoryReference;
    private String refSupplyOrderNumber;

    @Override
    public String toString() {

        return new JSONObject(this).toString();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookDTO other = (BookDTO) obj;
        if (serialNumber == null) {
            if (other.serialNumber != null)
                return false;
        } else if (!serialNumber.equals(other.serialNumber))
            return false;
        return true;
    }
}
