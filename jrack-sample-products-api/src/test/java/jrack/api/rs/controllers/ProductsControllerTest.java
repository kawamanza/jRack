package jrack.api.rs.controllers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jrack.api.rs.controllers.ProductsController;
import jrack.test.AbstractTestCase;

public class ProductsControllerTest extends AbstractTestCase {

	@Autowired
	private ProductsController controller;

	@Test
	public void testAutowiredController() {
		assertNotNull(controller);
	}
}
