package DanielaVillalta_20240286.DanielaVillalta_20240286.Service;

import DanielaVillalta_20240286.DanielaVillalta_20240286.Entity.ProviderEntity;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Exceptions.ExceptionProviderNotFound;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Exceptions.ExceptionProviderNotRegistered;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Models.DTO.ProviderDTO;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Repository.ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j //Permite utilizar log para mostrar mensajes en la consola
@Service
public class ProviderService {
    @Autowired
    ProviderRepository repo;

    //Convierte todos los campos de formato entity a formato dto
    private ProviderDTO convertirADTO(ProviderEntity provider) {
        ProviderDTO dto = new ProviderDTO();
        dto.setId(provider.getId());
        dto.setName(provider.getName());
        dto.setPhone(provider.getPhone());
        dto.setAddress(provider.getAddress());
        dto.setEmail(provider.getEmail());
        dto.setCode(provider.getCode());
        dto.setStatus(provider.getStatus());
        dto.setComments(provider.getComments());
        return dto;
    }

    //Convierte todos los campos de formato dto a formato entity
    private ProviderEntity convertirAEntity(ProviderDTO data) {
        ProviderEntity entity = new ProviderEntity();
        entity.setName(data.getName());
        entity.setPhone(data.getPhone());
        entity.setAddress(data.getAddress());
        entity.setEmail(data.getEmail());
        entity.setCode(data.getCode());
        entity.setStatus(data.getStatus());
        entity.setComments(data.getComments());
        return entity;
    }

    //Obtiene los proveedores y los convierte a formato dto
    public List<ProviderDTO> obtenerProveedores() {
        List<ProviderEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //Inserta los datos del nuevo proveedor en formato entity
    public ProviderDTO insertarDatos(ProviderDTO data) {
        if (data == null || data.getEmail().isEmpty() || data.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Correo electrónico o número de teléfono no pueden estar vacíos");
        }
        try {
            ProviderEntity entity = convertirAEntity(data);
            ProviderEntity proveedorGuardado = repo.save(entity);
            return convertirADTO(proveedorGuardado);
        } catch (Exception e) {
            log.error("Error al registrar el usuario: " + e.getMessage());
            throw new ExceptionProviderNotRegistered("Error al registrar el proveedor.");
        }
    }

    //Actualiza los datos del proveedor seleccionado en formato entity
    public ProviderDTO actualizarProveedor(Long id, ProviderDTO json) {
        ProviderEntity existente = repo.findById(id).orElseThrow(() -> new ExceptionProviderNotFound("Proveedor no encontrado."));
        existente.setName(json.getName());
        existente.setPhone(json.getPhone());
        existente.setAddress(json.getAddress());
        existente.setEmail(json.getEmail());
        existente.setCode(json.getCode());
        existente.setStatus(json.getStatus());
        existente.setComments(json.getComments());
        ProviderEntity proveedorActualizado = repo.save(existente);
        return convertirADTO(proveedorActualizado);
    }

    //Borra a un proveedor seleccionado
    public boolean removerProveedor(Long id) {
        try {
            ProviderEntity existente = repo.findById(id).orElse(null);
            if (existente != null) {
                repo.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("No se encontró el proveedor con ID " + id + " para eliminar", 1);
        }
    }

    //Busca proveedores por su nombre, utilizando un método de Entity, y los lista
    public List<ProviderDTO> buscarPorNombre (String nombre) {
        List<ProviderEntity> entidad = repo.findByNameContainingIgnoreCase(nombre);
        return entidad.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
