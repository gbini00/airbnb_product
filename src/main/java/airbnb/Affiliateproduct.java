package airbnb;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Affiliateproduct_table")
public class Affiliateproduct {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long prdId;
    private String prdNm;
    private Long qty;
    private String desc;
	private String status;

    @PostPersist
    public void onPostPersist(){
        ProductRegistered productRegistered = new ProductRegistered();
        BeanUtils.copyProperties(this, productRegistered);
        productRegistered.publishAfterCommit();

	}
	
	
    @PostUpdate
    public void onPostUpdate(){
		if(status.equals("modifyProduct")) {
			ProductModified productModified = new ProductModified();
			BeanUtils.copyProperties(this, productModified);
			productModified.publishAfterCommit();
		}

		if(status.equals("orderConfirm")) {
			ProductOrdered productOrdered = new ProductOrdered();
			BeanUtils.copyProperties(this, productOrdered);
			productOrdered.publishAfterCommit();
		}

		if(status.equals("orderCancel")) {
			ProductCancelled productCancelled = new ProductCancelled();
			BeanUtils.copyProperties(this, productCancelled);
			productCancelled.publishAfterCommit();
		}
	}
	
    @PreRemove
    public void onPreRemove(){
		ProductDeleted productDeleted = new ProductDeleted();
		BeanUtils.copyProperties(this, productDeleted);
		productDeleted.publishAfterCommit();
	}

    public Long getPrdId() {
        return prdId;
    }

    public void setPrdId(Long prdId) {
        this.prdId = prdId;
    }
    public String getPrdNm() {
        return prdNm;
    }

    public void setPrdNm(String prdNm) {
        this.prdNm = prdNm;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
	
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



}
