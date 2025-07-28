package DanielaVillalta_20240286.DanielaVillalta_20240286.Controller;

import DanielaVillalta_20240286.DanielaVillalta_20240286.Exceptions.ExceptionDuplicatedData;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Exceptions.ExceptionProviderNotFound;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Models.DTO.ProviderDTO;
import DanielaVillalta_20240286.DanielaVillalta_20240286.Service.ProviderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiProviders")
public class ProviderController {
    @Autowired
    ProviderService service;

    @GetMapping("getProviders")
    public List<ProviderDTO> obtenerDatos() {
        return service.obtenerUsuarios();
    }

    @PostMapping("/postProvider")
    public ResponseEntity<?> nuevoProveedor(@Valid @RequestBody ProviderDTO json, HttpServletRequest request) {
        try {
            ProviderDTO respuesta = service.insertarDatos(json);
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "El proveedor no pudo ser registrado."
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "Error",
                            "message", "Error no controlado al registrar el proveedor.",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/editProvider/{id}")
    public ResponseEntity<?> modificarProveedor(
            @PathVariable Long id,
            @Valid @RequestBody ProviderDTO json,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            ProviderDTO dto = service.actualizarProveedor(id, json);
            return ResponseEntity.ok(dto);
        } catch (ExceptionProviderNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (ExceptionDuplicatedData e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "DatosDuplicados",
                    "Campo", e.getCampoDuplicado())
            );
        }
    }

    @DeleteMapping("/deleteProvider/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            if (!service.removerProveedor(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje error", "Usuario no encontrado")
                        .body(Map.of(
                                "Error", "Not found",
                                "Message", "El proveedor no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok(Map.of(
                    "status", "Proceso completado",
                    "message", "Proveedor eliminado exitosamente."
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                    "status", "error",
                    "message", "Error al eliminar el proveedor.",
                    "detail", e.getMessage()
            ));
        }
    }
}