package airbnb;

import airbnb.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired AffiliateproductRepository affiliateproductRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCreateConfirmed_ConfirmOrder(@Payload OrderCreateConfirmed orderCreateConfirmed){

        if(!orderCreateConfirmed.validate()) return;

        System.out.println("\n\n##### listener ConfirmOrder : " + orderCreateConfirmed.toJson() + "\n\n");

		long prdId = orderCreateConfirmed.getPrdId();
		long qty = orderCreateConfirmed.getQty();
        String status = "orderConfirm";

		updateProductStatus (prdId, qty, status);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelConfirmed_CancelOrder(@Payload OrderCancelConfirmed orderCancelConfirmed){

        if(!orderCancelConfirmed.validate()) return;

        System.out.println("\n\n##### listener CancelOrder : " + orderCancelConfirmed.toJson() + "\n\n");

		long prdId = orderCancelConfirmed.getPrdId();
		long qty = orderCancelConfirmed.getQty();
        String status = "orderCancel";

		updateProductStatus(prdId, qty, status);
            
    }

    private void updateProductStatus(long prdId, long qty, String status)     {
        Optional<Affiliateproduct> res = affiliateproductRepository.findById(prdId);
        Affiliateproduct affiliateproduct = res.get();

        System.out.println("prdId      : " + affiliateproduct.getPrdId());
        System.out.println("prodNm      : " + affiliateproduct.getPrdNm());
        System.out.println("qty  : " + affiliateproduct.getQty());
		System.out.println("desc  : " + affiliateproduct.getDesc());

        affiliateproduct.setQty(affiliateproduct.getQty() - qty);
        affiliateproduct.setStatus(status);


        affiliateproductRepository.save(affiliateproduct);

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
