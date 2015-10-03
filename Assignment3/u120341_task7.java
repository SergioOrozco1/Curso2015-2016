package ontologyapi;

import java.io.InputStream;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

/**
 * Task 07: Querying ontologies (RDFs)
 * @author Javier
 * @author Oliva
 *
 */
public class Task07
{
	public static String ns = "http://somewhere#";
	
	public static void main(String args[])
	{
		String filename = "example6.rdf";
		
		// Create an empty model
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM);
		
		// Use the FileManager to find the input file
		InputStream in = FileManager.get().open(filename);
	
		if (in == null)
			throw new IllegalArgumentException("File: "+filename+" not found");
	
		// Read the RDF/XML file
		model.read(in, null);
				
		// ** TASK 7.1: List all individuals of "Person" **
		OntClass person = model.getOntClass(ns+"Person");
		ExtendedIterator<Individual> it = model.listIndividuals(person);
		
		System.out.println("All individuals of Person:");
		while (it.hasNext())
		{
			Individual i = it.next();
			System.out.println(i.getURI());
		}
		
		// ** TASK 7.2: List all subclasses of "Person" **
		ExtendedIterator<OntClass> it2 = person.listSubClasses();
		
		System.out.println("All subclasses of Person:");
		while(it2.hasNext())
		{
			OntClass i = it2.next();
			System.out.println(i.getURI());
		}
		
		
		// ** TASK 7.3: Make the necessary changes to get as well indirect instances and subclasses. TIP: you need some inference... **
		
		// We create a new model with inference
		OntModel modelWithInference = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM_RDFS_INF);
		InputStream in2 = FileManager.get().open(filename);
		modelWithInference.read(in2, null);
		
		OntClass personWI = modelWithInference.getOntClass(ns+"Person");
		
		// We list all indirect individuals of Person
		ExtendedIterator<Individual> it3 = modelWithInference.listIndividuals(personWI);
		
		System.out.println("All individuals of Person (with inference):");
		while (it3.hasNext())
		{
			Individual i = it3.next();
			System.out.println(i.getURI());
		}
		
		// We list all indirect subclasses of Person
		ExtendedIterator<OntClass> it4 = personWI.listSubClasses();
		
		System.out.println("All subclasses of Person (with inference):");
		while(it4.hasNext())
		{
			OntClass i = it4.next();
			System.out.println(i.getURI());
		}	
	}
}
