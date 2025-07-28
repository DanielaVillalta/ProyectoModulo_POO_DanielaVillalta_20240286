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

    //Obtiene la lista de proveedores
    @GetMapping("/getProviders") //localhost:8080/apiProviders/getProviders
    public List<ProviderDTO> obtenerDatos() {
        return service.obtenerProveedores();
    }

    //Inserta un proveedor
    @PostMapping("/postProvider") //localhost:8080/apiProviders/postProvider
    public ResponseEntity<?> nuevoProveedor(@Valid @RequestBody ProviderDTO json, HttpServletRequest request) {
        try {
            ProviderDTO respuesta = service.insertarDatos(json);
            //Si los datos no pueden ser insertados, muestra un mensaje de error
            if (respuesta == null) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "Inserción fallida",
                        "errorType", "VALIDATION_ERROR",
                        "message", "El proveedor no pudo ser registrado."
                ));
            }
            //Si los datos fueron insertados correctamente, muestra un mensaje de éxito
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", "success",
                    "data", respuesta
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) //Si ocurre un error inesperado, muestra este mensaje de error
                    .body(Map.of(
                            "status", "Error",
                            "message", "Error no controlado al registrar el proveedor.",
                            "detail", e.getMessage()
                    ));
        }
    }

    //Edita la información de un proveedor seleccionado por su ID
    @PutMapping("/editProvider/{id}") //localhost:8080/apiProviders/editProvider/{id}
    public ResponseEntity<?> modificarProveedor(
            @PathVariable Long id, //Reconoce el ID como la variable
            @Valid @RequestBody ProviderDTO json,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) { //Si la actualización tiene errores, muestra un mensaje por cada uno
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            //Si todo sale bien, muestra un mensaje de éxito y el json del proveedor actualizado
            ProviderDTO dto = service.actualizarProveedor(id, json);
            return ResponseEntity.ok(dto);
        } catch (ExceptionProviderNotFound e) {
            return ResponseEntity.notFound().build();
        } catch (ExceptionDuplicatedData e) {
            //Si hay datos duplicados, muestra el campo
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "Error", "DatosDuplicados",
                    "Campo", e.getCampoDuplicado())
            );
        }
    }

    //Borra un proveedor
    @DeleteMapping("/deleteProvider/{id}") //localhost:8080/apiProviders/deleteProvider/{id}
    public ResponseEntity<?> eliminarProveedor(@PathVariable Long id) {
        try {
            if (!service.removerProveedor(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .header("Mensaje error", "Proveedor no encontrado") //Si el ID no pertenece a ningún proveedor, muestra el mensaje de que no fue encontrado
                        .body(Map.of(
                                "Error", "Not found",
                                "Message", "El proveedor no ha sido encontrado",
                                "timestamp", Instant.now().toString()
                        ));
            }
            return ResponseEntity.ok(Map.of( //Si sale bien, muestra un mensaje de éxito
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

    //Busca a un proveedor por su nombre
    @GetMapping("/buscar") //localhost:8080/apiProviders/buscar?nombre={busqueda}
    public ResponseEntity<?> buscarProveedores (@RequestParam String nombre) {
        List<ProviderDTO> resultados = service.buscarPorNombre(nombre);
        if (resultados.isEmpty()) { //Si el nombre de ningún proveedor coincide con el valor ingresado, muestra un mensaje
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron usuarios.");
        }
        //Si hay coincidencias, las muestra
        return ResponseEntity.ok(resultados);
    }
}