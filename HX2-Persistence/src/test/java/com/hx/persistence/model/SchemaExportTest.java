package com.hx.persistence.model;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;

import com.hx.persistence.CommonTezt;
import com.hx.persistence.model.a.A;
import com.hx.persistence.model.b.B;

public class SchemaExportTest extends CommonTezt {

	@Test
	public void testSchemaExport() throws Exception {

		this.configureLog4j();

		AnnotationConfiguration configuration = this
				.createAnnotationConfiguration("jdbc:hsqldb:mem:"
						+ this.getClass().getName());
		configuration.addAnnotatedClass(A.class);
		configuration.addAnnotatedClass(B.class);

		SchemaExport schemaExport = new SchemaExport(configuration);
		schemaExport.create(false, true);
	}
}
