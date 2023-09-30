package com.alcon3sl.cms.services.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import com.alcon3sl.cms.exception.CarrierNotFoundException;
import com.alcon3sl.cms.model.carrier.KindCarrier;
import com.alcon3sl.cms.model.util.contact_details.ContactDetails;
import com.alcon3sl.cms.repository.carrier.CarrierRepository;
import com.alcon3sl.cms.model.util.JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DbCarrierService implements CarrierService {
    private final CarrierRepository carrierRepository;
    @Autowired
    public DbCarrierService(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    @Override
    public Page<Carrier> findAll(String filter, PageRequest pageRequest) {
        if (filter.isEmpty())
            return carrierRepository.findAll(pageRequest);
        String[] filters = filter.replaceAll("\s+", " ").split(" ");
        List<Carrier> carrierList = new ArrayList<>();
        for (String current : filters) {
            List<Carrier> subList = carrierRepository.findAllByNameOrKindCarrier(current);
            carrierList = JUtil.refineList(carrierList, subList);
        }

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), carrierList.size());

        List<Carrier> subList = carrierList.subList(start, end);
        return new PageImpl<>(subList, pageRequest, carrierList.size());
    }

    @Override
    public Carrier findById(Long carrierId) {
        return carrierRepository.findById(carrierId)
                .orElseThrow(() -> new CarrierNotFoundException("Carrier not found"));
    }

    @Override
    public Carrier save(Carrier carrier) {
        boolean flag = carrier == null || carrier.getName().isEmpty();
        if (flag)
            throw new CarrierNotFoundException("Wrong data");
        if (!carrierRepository.findByName(carrier.getName().trim().toUpperCase()).isEmpty())
            throw new CarrierNotFoundException("The name already exists");
        if (!carrierRepository.findByCif(carrier.getCif().trim().toUpperCase()).isEmpty())
            throw new CarrierNotFoundException("The cif already exists");

        return carrierRepository.save(carrier);
    }

    @Override
    public Carrier deleteById(Long carrierId) {
        var carrier = this.findById(carrierId);
        carrierRepository.deleteById(carrierId);
        return carrier;
    }

    @Override
    public Carrier updateById(Long carrierId, Carrier tempData) {
        var carrier = this.findById(carrierId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(carrier.getName(), name)) {
            if (!carrierRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new CarrierNotFoundException("The name already exists");
            else
                carrier.setName(name);
        }

        String description = tempData.getDescription();
        if (description != null && !description.isEmpty() && !Objects.equals(carrier.getDescription(), description))
            carrier.setDescription(description);

        String cif = tempData.getCif();
        if (cif != null && !cif.isEmpty() && !Objects.equals(carrier.getCif(), cif)) {
            if (!carrierRepository.findByCif(cif.trim().toUpperCase()).isEmpty())
                throw new CarrierNotFoundException("The cif already exists");
            else
                carrier.setCif(cif);
        }

        KindCarrier kindCarrier = tempData.getKindCarrier();
        if (kindCarrier != null && !Objects.equals(carrier.getKindCarrier(), kindCarrier))
            carrier.setKindCarrier(kindCarrier);

        ContactDetails contactDetails = tempData.getContactDetails();
        if (contactDetails != null && Objects.equals(carrier.getContactDetails(), contactDetails))
            carrier.setContactDetails(contactDetails);

        return carrierRepository.save(carrier);
    }

    @Override
    public List<Carrier> deleteAllById(List<Long> listId) {
        var carrierList = carrierRepository.findAllById(listId);
        carrierRepository.deleteAllById(listId);
        return carrierList;
    }
}
