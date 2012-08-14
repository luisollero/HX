package com.hx.model.dao;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.dao.DataAccessException;

public interface IDAOCommon<M> {

	/**
	 * Returns a certain object
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	M getById(Serializable id) throws DataAccessException;

	/**
	 * Returns a certain object with all its collections loaded
	 * 
	 * @param id
	 * @return the object
	 */
	M getWithLaziesById(Serializable id);

	/**
	 * Persiste los cambios realizados sobre un elemento Object o crea uno nuevo
	 * si no existe
	 * 
	 * @param object
	 *            el objeto con los nuevos valores a persistir
	 * @return el resultado de la operación
	 * @throws Exception
	 */
	void saveOrUpdate(M object) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre una colección de elementos Object
	 * o los crea nuevos si no existen
	 * 
	 * @param objects
	 *            la colección de objetos a actualizar/crear
	 * @return el resultado de la operación
	 * @throws Exception
	 */
	void saveOrUpdateAll(Collection<M> objects) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre un elemento Object
	 * 
	 * @param object
	 *            el objeto con los nuevos valores a persistir
	 * @return el resultado de la operación
	 * @throws Exception
	 */
	void update(M object) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre una colección de elementos Object
	 * 
	 * @param objects
	 *            la colección de objetos a actualizar
	 * @return el resultado de la operación
	 * @throws Exception
	 */
	void updateAll(Collection<M> objects) throws DataAccessException;

	/**
	 * Obtiene todos los elementos de la tabla. Si sus colecciones, en caso de
	 * que tenga, están definidas en el DTO (POJO) como LAZY (por defecto), no
	 * se cargarán, lanzando una excepción LazyInitializationException al
	 * intentar acceder a alguna de ellas. En ese caso se debe usar el método
	 * findWithLazies().
	 * 
	 * @return una lista con los elementos
	 * @throws Exception
	 */
	Collection<M> find() throws DataAccessException;

	/**
	 * Obtiene todos los elementos de la tabla. Si tiene colecciones de datos,
	 * siempre las cargará para permitir su acceso. Si no se precisa acceder a
	 * los datos de las colecciones se debe usar el método find() pues es mucho
	 * más ligero.
	 * 
	 * @return una lista con los elementos
	 * @throws DataAccessException
	 */
	Collection<M> findWithLazies() throws DataAccessException;

	/**
	 * Deletes a certain object
	 * 
	 * @param object
	 *            el identificador del elemento a eliminar
	 * @throws Exception
	 */
	void delete(M object) throws DataAccessException;

	/**
	 * Deletes a list of objects
	 * 
	 * @param objects
	 *            la colección de objetos a eliminar
	 * @throws Exception
	 */
	void deleteAll(Collection<M> objects) throws DataAccessException;


	/**
	 * Simulates the final commit of a transaction to force the changes being
	 * available inside of the same transaction. To use normally before the
	 * <code>refresh</code> to update an object within the session.
	 */
	void flush();

	/**
	 * Reads an object to keep it updated
	 * 
	 * @param organization
	 */
	void refresh(M object);

	M merge(M object);
}
