package com.hx.model.dao;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.dao.DataAccessException;

public interface IDAOCommon<M> {

	/**
	 * Devuelve el elemento en una busqueda por Id
	 * 
	 * @param id
	 * @return
	 * @throws DataAccessException
	 */
	M getById(Serializable id) throws DataAccessException;

	/**
	 * Devuelve el elemento en una busqueda por Id. Devuelve sus colecciones cargadas
	 * @param id	identificador del elemento a buscar
	 * @return		el elementos cuyo id es el indicado o null si no existe
	 */
	M getWithLaziesById(Serializable id);

	/**
	 * Persiste los cambios realizados sobre un elemento Object o crea
	 * uno nuevo si no existe
	 * @param object	el objeto con los nuevos valores a persistir
	 * @return	el resultado de la operación
	 * @throws Exception
	 */
	void saveOrUpdate(M object) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre una colección de elementos
	 * Object o los crea nuevos si no existen
	 * @param objects	la colección de objetos a actualizar/crear
	 * @return	el resultado de la operación
	 * @throws Exception
	 */
	void saveOrUpdateAll(Collection<M> objects) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre un elemento Object
	 * @param object	el objeto con los nuevos valores a persistir
	 * @return	el resultado de la operación
	 * @throws Exception
	 */
	void update(M object) throws DataAccessException;

	/**
	 * Persiste los cambios realizados sobre una colección de elementos
	 * Object
	 * @param objects	la colección de objetos a actualizar
	 * @return	el resultado de la operación
	 * @throws Exception
	 */
	void updateAll(Collection<M> objects) throws DataAccessException;

	/**
	 * Obtiene todos los elementos de la tabla. Si sus colecciones, en caso de que tenga,
	 * están definidas en el DTO (POJO) como LAZY (por defecto), no se cargarán, lanzando
	 * una excepción LazyInitializationException al intentar acceder a alguna de ellas.
	 * En ese caso se debe usar el método findWithLazies().
	 * @return	una lista con los elementos
	 * @throws Exception
	 */
	Collection<M> find() throws DataAccessException;

	/**
	 * Obtiene todos los elementos de la tabla. Si tiene colecciones de datos, siempre
	 * las cargará para permitir su acceso. Si no se precisa acceder a los datos de las
	 * colecciones se debe usar el método find() pues es mucho más ligero.
	 * @return	una lista con los elementos
	 * @throws DataAccessException
	 */
	Collection<M> findWithLazies() throws DataAccessException;
	
	/**
	 * Elimina de la BD el elemento indicado
	 * @param object el identificador del elemento a eliminar
	 * @throws Exception
	 */
	void delete(M object) throws DataAccessException;

	/**
	 * Elimina de la BD los elementos indicados
	 * @param objects	la colección de objetos a eliminar
	 * @throws Exception
	 */
	void deleteAll(Collection <M> objects) throws DataAccessException;
	
	/**
	 * Obtiene la lista de elementos que cumplan el ejemplo
	 * @param example Object con los valores del ejemplo
	 * @return List lista de elementos buscados 
	 * @throws DAOException
	 */
//	public List<M> findByExample(M example) throws DataAccessException;

	/**
	 * Simula el commit propio del final de una transacción para que los cambios
	 * realizados estém disponibles en la BD dentro de la misma transacción.
	 * Suele usarse antes de <code>refresh</code> para actualziar un objeto
	 * de sesión.
	 */
	void flush();

	/**
	 * Relee de la BD un objeto para tenerlo disponible actualizado.
	 * @param organization
	 */
	void refresh(M object);

	M merge(M object);
}
