package uo.ri.cws.application.service.util;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import uo.ri.cws.application.business.client.ClientDto;
import uo.ri.cws.application.service.util.sql.AddClientSqlUnitOfWork;
import uo.ri.cws.application.service.util.sql.FindClientByDNISqlUnitOfWork;

public class ClientUtil {

	private ClientDto dto = createDefaultClient();

	public ClientUtil randomDni() {
		dto.dni = RandomStringUtils.randomAlphanumeric(9);
		return this;
	}

	public ClientUtil register() {
		new AddClientSqlUnitOfWork(dto).execute();
		return this;
	}

	public ClientDto get() {
		return dto;
	}

	private ClientDto createDefaultClient() {
		ClientDto res = new ClientDto();
		res.id = UUID.randomUUID().toString();
		res.version = 1L;
		res.dni = RandomStringUtils.randomAlphanumeric(9);
		res.name = RandomStringUtils.randomAlphabetic(4) + " name";
		res.surname = RandomStringUtils.randomAlphabetic(6) + " surname";
		res.email = RandomStringUtils.randomAlphabetic(6) + " email";
		res.phone = RandomStringUtils.randomAlphabetic(6) + " phone";		
		res.addressCity = RandomStringUtils.randomAlphabetic(6) + " city";
		res.addressStreet = RandomStringUtils.randomAlphabetic(6) + " street";
		res.addressZipcode = RandomStringUtils.randomAlphabetic(6) + " ZIP CODE";
		return res;
	}

	public ClientUtil withName(String string) {
		dto.name = string;
		return this;
	}

	public ClientUtil withSurname(String string) {
		dto.surname = string;
		return this;
	}

	public ClientUtil withDni(String string) {
		dto.dni = string;
		return this;

	}
	
	public ClientDto findByDni(String dni) {
		FindClientByDNISqlUnitOfWork finder = new FindClientByDNISqlUnitOfWork(dni);
		finder.execute();
		return finder.get().get();
	}

	public ClientUtil registerIfNew() {
		FindClientByDNISqlUnitOfWork finder = new FindClientByDNISqlUnitOfWork(dto.dni);
		finder.execute();
		
		Optional<ClientDto> op = finder.get(); 
		if ( op.isEmpty() ) {
			register();
		}
		else {
			this.dto = op.get();
		}
		return this;
	}

//	public class ClientDto {
//		public String id;
//		public Long version;
//		
//		public String dni;
//		public String name;
//		public String surname;
//		public String phone;
//		public String email;
//		public String addressStreet;
//		public String addressCity;
//		public String addressZipcode;
//	}
	
}
