package ch.supsi.webapp;

import ch.supsi.webapp.model.*;
import ch.supsi.webapp.repository.*;
import ch.supsi.webapp.service.GroupService;
import ch.supsi.webapp.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class WebApplication {

	@Autowired
	private PasswordService passwordService;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(SubCategoryRepsitory subCategoryRepsitory,GroupService groupRepository, ItemRepository itemRepository, CategoryRepository categoryRepository, RoleRepository roleRepository, UserRepository userRepository) {
		return (args) -> {
			categoryRepository.deleteAll();
			itemRepository.deleteAll();
			subCategoryRepsitory.deleteAll();
			groupRepository.deleteAll();

			userRepository.deleteAll();

			roleRepository.deleteAll();


			User sam = new User("Smarty", "Samuel", "Martins", new Role("admin"), passwordService.crypt("Samuel"));
			User admin = new User("admin", "Samuel", "Martins", new Role("admin"), passwordService.crypt("admin"));
			userRepository.save(sam);
			userRepository.save(admin);

			Category animals = new Category("Animals");
			SubCategory dog = new SubCategory("dog", animals);
			animals.getSubCategory().add(dog);

			Category tech = new Category("Tech");
			SubCategory pc = new SubCategory("pc", tech);
			tech.getSubCategory().add(pc);

			Category vehicles = new Category("Vehicles");
			SubCategory berlina = new SubCategory("berlina", vehicles);
			SubCategory sportiva = new SubCategory("sportiva", vehicles);


			subCategoryRepsitory.save(dog);
			subCategoryRepsitory.save(pc);
			subCategoryRepsitory.save(berlina);
			subCategoryRepsitory.save(sportiva);
			vehicles.getSubCategory().add(berlina);
			vehicles.getSubCategory().add(sportiva);
//			categoryRepository.save(vehicles);
//			categoryRepository.save(animals);
//			categoryRepository.save(tech);


			List<Item> items = new ArrayList<>();
			items.add(new Item("Cerco automobile",
					"Ho bisogno di un automobile automatica molto economica, solo persone serie.",
					sam, berlina, Announce.REQUEST,
					Files.readAllBytes(fileFromResources("car.jpeg"))));
			items.add(new Item("MacBook Pro 13\" 2019 - i7",
					"Vendo per passaggio ad un altro computer MacBook Pro (13\", 2019, 2 porte Thunderbolt 3) argento, comprato a febbraio 2020 ed in ottime condizioni. Completo di carica batterie e imballo originale, garanzia Apple fino a febbraio 2021 e fino a febbraio 2022 coperta dal rivenditore.",
					admin, pc, Announce.OFFER,
					Files.readAllBytes(fileFromResources("mac.jpeg"))));
			items.add(new Item("Vendo gatto grasso",
					"Vendo gatto grasso molto bello e peloso, offerta d anon perdere!!!",
					sam, dog, Announce.OFFER,
					Files.readAllBytes(fileFromResources("fatcat.jpeg"))));
			items.add(new Item("Vendo telefonino NATEL",
					"Vendo NATEL solo a ticinesi, se non siete ticinesi non chiamate!!!",
					sam, pc, Announce.OFFER,
					Files.readAllBytes(fileFromResources("3310.jpeg"))));
			items.add(new Item("Cerco cane scomparso",
					"Aiuto sono molto triste!!!",
					sam, sportiva, Announce.REQUEST,
					Files.readAllBytes(fileFromResources("pug.jpeg"))));

			itemRepository.saveAll(items);

		};
	}

	private Path fileFromResources(String name) {
		String path = Objects.requireNonNull(getClass().getClassLoader().getResource("demo/img/"+name)).getFile();
		return new File(path).toPath();
	}

}
