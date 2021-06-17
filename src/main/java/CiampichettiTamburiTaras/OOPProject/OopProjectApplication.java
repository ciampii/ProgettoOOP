package CiampichettiTamburiTaras.OOPProject;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import CiampichettiTamburiTaras.OOPProject.Model.Data.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages={"CiampichettiTamburiTaras.OOPProject.Controller", "CiampichettiTamburiTaras.OOPProject.Model",
					"CiampichettiTamburiTaras.OOPProject.Model.Data", "CiampichettiTamburiTaras.OOPProject.Utils"})
public class OopProjectApplication {

	public static void main(String[] args) {
	 	SpringApplication.run(OopProjectApplication.class, args);
		
	}
}
