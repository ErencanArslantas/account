package com.folksdev.account.service;

import com.folksdev.account.dto.CustomerAccountDto;
import com.folksdev.account.dto.CustomerDto;
import com.folksdev.account.dto.CustomerDtoConverter;
import com.folksdev.account.exception.CustomerNotFoundException;
import com.folksdev.account.model.Customer;
import com.folksdev.account.repository.CustomerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;

public class CustomerServiceTest {

  private CustomerService service;
  private CustomerRepository customerRepository;
  private CustomerDtoConverter converter;
// private static metotların testleri yazılmaz
  @BeforeEach
  public void setUp(){
   customerRepository = mock(CustomerRepository.class);
   converter = mock(CustomerDtoConverter.class);
   service = new CustomerService(customerRepository,converter);

  }
    @Test
    public void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer = new Customer("id","name","surname",Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = service.findCustomerById("id");

        assertEquals(result,
                customer);
    }
  @Test
 public void testFindByCustomerId_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException(){



      Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

      assertThrows(CustomerNotFoundException.class, () -> service.findCustomerById("id"));


  }
  @Test
  public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer(){
      Customer customer = new Customer("id","name","surname",Set.of());
      CustomerDto customerDto = new CustomerDto("id","name ","surname",Set.of());

      Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

      Mockito.when(converter.convertToCustomerDto(customer)).thenReturn(customerDto);

      CustomerDto  result = service.getCustomerById("id");

      assertEquals(result,customer);
  }
    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExist_shouldThrowCustomerNotFoundException() {


        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());


        assertThrows(CustomerNotFoundException.class, () -> service.getCustomerById("id"));

        Mockito.verifyNoInteractions(converter); //converterın hiçbir methodunu çağırma

    }

}