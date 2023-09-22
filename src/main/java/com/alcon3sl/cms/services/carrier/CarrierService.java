package com.alcon3sl.cms.services.carrier;

import com.alcon3sl.cms.model.carrier.Carrier;
import com.alcon3sl.cms.model.carrier.CarrierException;
import com.alcon3sl.cms.model.carrier.KindCarrier;
import com.alcon3sl.cms.repository.carrier.CarrierRepository;
import com.alcon3sl.cms.utils.JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CarrierService {
    private final CarrierRepository carrierRepository;
    @Autowired
    public CarrierService(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

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

    public Carrier findById(Long carrierId) {
        return carrierRepository.findById(carrierId)
                .orElseThrow(() -> new CarrierException("Carrier not found"));
    }

    public Carrier save(Carrier carrier) {
        boolean flag = carrier == null || carrier.getName().isEmpty();
        if (flag)
            throw new CarrierException("Wrong data");
        if (!carrierRepository.findByName(carrier.getName().trim().toUpperCase()).isEmpty())
            throw new CarrierException("The name already exists");
        if (!carrierRepository.findByCif(carrier.getCif().trim().toUpperCase()).isEmpty())
            throw new CarrierException("The cif already exists");

        return carrierRepository.save(carrier);
    }

    public Carrier deleteById(Long carrierId) {
        var carrier = this.findById(carrierId);
        carrierRepository.deleteById(carrierId);
        return carrier;
    }

    public Carrier updateById(Long carrierId, Carrier tempData) {
        var carrier = this.findById(carrierId);

        String name = tempData.getName();
        if (name != null && !name.isEmpty() && !Objects.equals(carrier.getName(), name)) {
            if (!carrierRepository.findByName(name.trim().toUpperCase()).isEmpty())
                throw new CarrierException("The name already exists");
            else
                carrier.setName(name);
        }

        String description = tempData.getDescription();
        if (description != null && !description.isEmpty() && !Objects.equals(carrier.getDescription(), description))
            carrier.setDescription(description);

        String cif = tempData.getCif();
        if (cif != null && !cif.isEmpty() && !Objects.equals(carrier.getCif(), cif)) {
            if (!carrierRepository.findByCif(cif.trim().toUpperCase()).isEmpty())
                throw new CarrierException("The cif already exists");
            else
                carrier.setCif(cif);
        }

        KindCarrier kindCarrier = tempData.getKindCarrier();
        if (kindCarrier != null && !Objects.equals(carrier.getKindCarrier(), kindCarrier))
            carrier.setKindCarrier(kindCarrier);

        return carrierRepository.save(carrier);
    }

    public List<Carrier> findAllByListId(List<Long> listId) {
        return carrierRepository.findAllByListId(listId);
    }

    @Transactional
    public List<Carrier> deleteAllByListId(List<Long> listId) {
        var carrierList = this.findAllByListId(listId);
        carrierRepository.deleteAllByListId(listId);
        return carrierList;
    }
}
