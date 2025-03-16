package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.mapper.AddressMapper;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.repositories.AddressRepository;
import com.ecommerce.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address = addressMapper.addressDTOToAddress(addressDTO);
        address.setUser(user);
        List<Address> addressesList = user.getAddresses();
        addressesList.add(address);
        /*
        https://www.udemy.com/course/spring-boot-using-intellij-build-a-real-world-project/learn/lecture/43984316#questions/22150569
        Yes, you can also use userRepo.save(user) to explicitly save the user along with the new address. This ensures that all changes, including those to the list of addresses, are committed to the database.
Regarding how JPA can save changes without directly calling the .save() method: In JPA, if an entity like your Address is managed and associated with a persistent context, any changes made to it during a transaction are automatically persisted when the transaction completes. This feature is often referred to as "dirty checking," where JPA detects changes made to the entity and automatically updates the database at the end of the transaction.
         */
        user.setAddresses(addressesList);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.addressToAddressDTO(savedAddress);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> {
                    return addressMapper.addressToAddressDTO(address);
                })
                .toList();
    }

    @Override
    public AddressDTO getAddressesById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
        return addressMapper.addressToAddressDTO(address);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
        List<Address> addresses = user.getAddresses();
        return addresses.stream()
                .map(address -> {
                    return addressMapper.addressToAddressDTO(address);
                })
                .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setPincode(addressDTO.getPincode());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuildingName(addressDTO.getBuildingName());

        Address updatedAddress = addressRepository.save(addressFromDatabase);

        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);
        userRepository.save(user);

        return addressMapper.addressToAddressDTO(updatedAddress);//modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        User user = addressFromDatabase.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDatabase);

        return "Address deleted successfully with addressId: " + addressId;
    }
}