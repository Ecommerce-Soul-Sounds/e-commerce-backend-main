package com.revature.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Order;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Address;
import com.revature.repositories.UserAddressRepository;
import com.revature.services.UserAddressService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserAddressServiceTest {
    @Mock 
    private static UserAddressRepository mockDao;

    @InjectMocks
	private static UserAddressService userAddressServ;

    private static Address address1, address2,address3;    
	private static List<Address> dummyDb;

    @BeforeAll
	static void setUpBeforeClass() throws Exception{
        mockDao = Mockito.mock(UserAddressRepository.class);
        userAddressServ = new UserAddressService(mockDao);

        address1 = new Address(1, "line1", "line2", "city", "state", 123);
        address2 = new Address(2, "line1", "line2", "city2", "state2", 321);

        dummyDb = new ArrayList<Address>();

        dummyDb.add(address1);
        dummyDb.add(address2);
    
    }

    @Test
	@Order(1)
	@DisplayName("1. Mock Validation Sanity Test")
	void checkMockInjection() {
		assertThat(mockDao).isNotNull();
		assertThat(userAddressServ).isNotNull();
    }

    @Test
	@Order(2)
	@DisplayName("2. Create Address")
	void TestCreateAddress() {

        address3 = new Address(2, "line3", "line3", "city3", "state3", 456);

		when(mockDao.save(address3)).thenReturn(address3);

		assertEquals(address3, userAddressServ.create(address3));
	
	}

    @Test
	@Order(3)
	@DisplayName("3. Get Address by Id")
	void TestFindById() {

        when(userAddressServ.findById(address1.getId())).thenReturn(address1);
        assertEquals(address1, userAddressServ.findById(address1.getId()));

    }

    @Test
	@Order(4)
	@DisplayName("4. Update Address")
	void TestUpdateAdress() {

        address1.setCity("TestCity");
        address1.setState("TestState");

        when(userAddressServ.findById(address1.getId())).thenReturn(address1);
		when(mockDao.save(address1)).thenReturn(address1);
		
		assertEquals(true, userAddressServ.update(address1));


    }

    @Test
	@Order(5)
	@DisplayName("5. Delete Address")
	void TestDeleteAddress() {
        doNothing().when(mockDao).delete(address1);
        //act + assert step
        assertEquals(true, userAddressServ.delete(address1));
        
	}





    
}
