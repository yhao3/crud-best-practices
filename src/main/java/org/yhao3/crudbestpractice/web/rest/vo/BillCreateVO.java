package org.yhao3.crudbestpractice.web.rest.vo;

import org.yhao3.crudbestpractice.service.dto.BillCreateDTO;

/**
 * The create bill view object.
 * @author haoyang
 */
public class BillCreateVO extends BillCreateDTO {
    public BillCreateVO() {
    }

    @Override
    public String toString() {
        return "BillCreateVO {" +
                "billDate=" + this.getBillDate() +
                ", billNo='" + this.getBillNo() + '\'' +
                ", billDetails=" + this.getBillDetails() +
                '}';
    }
}
