package DanielaVillalta_20240286.DanielaVillalta_20240286.Service;

import DanielaVillalta_20240286.DanielaVillalta_20240286.Entity.ProviderEntity;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Models.DTO.ProviderDTO;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Repository.ProviderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProviderService {
    @Autowired
    ProviderRepository repo;

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

    public List<ProviderDTO> obtenerUsuarios() {
        List<ProviderEntity> lista = repo.findAll();
        return lista.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ProviderDTO insertarDatos(ProviderDTO data) {
        if (data == null || data.getEmail().isEmpty() || data.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Correo electrónico o número de teléfono no pueden estar vacíos");
        }
        try {
            ProviderEntity entity = convertirAEntity(data);
            ProviderEntity usuarioGuardado = repo.save(entity);
            return convertirADTO(usuarioGuardado);
        }
    }

    public ProviderDTO actualizarProveedor(Long id, @Valid ProviderDTO json) {
        return null;
    }

    public boolean removerProveedor(Long id) {
        return false;
    }
}
