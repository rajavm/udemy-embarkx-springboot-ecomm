package com.ecommerce.project.mapper;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.payload.AddressDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    // Map single Address to AddressDTO
    AddressDTO addressToAddressDTO(Address address);

    // Map single AddressDTO to Address
    Address addressDTOToAddress(AddressDTO addresstDTO);
}
