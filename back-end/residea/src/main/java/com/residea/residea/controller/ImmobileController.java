package com.residea.residea.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.residea.residea.dto.ImmobileListDTO;
import com.residea.residea.entities.Immagine;
import com.residea.residea.entities.Immobile;
import com.residea.residea.entities.Utente;
import com.residea.residea.repos.UtenteRepo;
import com.residea.residea.services.ImmagineService;
import com.residea.residea.services.ImmobileService;

@RestController
@RequestMapping("/api/immobili")
public class ImmobileController {

    @Autowired
    private ImmobileService immobiliService;

    @Autowired
    private UtenteRepo utenteRepo;
    
    @Autowired
    private ImmagineService immagineService;
    
    @Value("${immagini.upload-dir}")
    private String uploadDir;

    // GET /api/immobili → restituisce lista di immobili
    @GetMapping
    public List<Immobile> getAllImmobili() {
        return immobiliService.getAllImmobili();
    }

    // POST /api/immobili → crea nuovo immobile
    @PostMapping
    public Immobile salvaImmobile(@RequestBody Immobile immobile) {
        // se viene passato proprietario con solo id, risolvi l'entità reale
        if (immobile.getProprietario() != null && immobile.getProprietario().getIdUtente() != null) {
            Integer pid = immobile.getProprietario().getIdUtente();
            Utente p = utenteRepo.findById(pid).orElse(null);
            immobile.setProprietario(p);
        } else {
            immobile.setProprietario(null);
        }
        return immobiliService.salvaImmobile(immobile);
    }

    // PUT /api/immobili → aggiorna immobile
    @PutMapping
    public Immobile aggiornaImmobile(@RequestBody Immobile immobile) {
        if (immobile.getProprietario() != null && immobile.getProprietario().getIdUtente() != null) {
            Integer pid = immobile.getProprietario().getIdUtente();
            Utente p = utenteRepo.findById(pid).orElse(null);
            immobile.setProprietario(p);
        } else {
            immobile.setProprietario(null);
        }
        return immobiliService.aggiornaImmobile(immobile);
    }

    // GET /api/immobili/{id}
    @GetMapping(path = "/{id}")
    public Immobile getImmobileById(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        return immobiliService.getImmobileById(id);
    }

    // GET /api/immobili/proprietario/{idUtente}
    @GetMapping(path = "/proprietario/{idUtente}")
    public java.util.List<Immobile> getImmobiliByProprietario(@org.springframework.web.bind.annotation.PathVariable("idUtente") String idUtenteStr) {
        if (idUtenteStr == null || idUtenteStr.isBlank()) return java.util.Collections.emptyList();
        Integer idUtente = null;
        try {
            idUtente = Integer.valueOf(idUtenteStr.trim());
        } catch (NumberFormatException ex) {
            return java.util.Collections.emptyList();
        }
        Utente p = utenteRepo.findById(idUtente).orElse(null);
        if (p == null) return java.util.Collections.emptyList();
        return immobiliService.getImmobiliByProprietario(p);
    }

    // GET /api/immobili/tipologia/{tipologia}
    @GetMapping(path = "/tipologia/{tipologia}")
    public java.util.List<Immobile> getImmobiliByTipologia(@org.springframework.web.bind.annotation.PathVariable("tipologia") String tipologia) {
        if (tipologia == null || tipologia.isBlank()) return java.util.Collections.emptyList();
        try {
            Immobile.Tipologia t = Immobile.Tipologia.valueOf(tipologia.trim().toUpperCase());
            return immobiliService.getImmobiliByTipologia(t);
        } catch (IllegalArgumentException ex) {
            return java.util.Collections.emptyList();
        }
    }
    
    // ====================
    // DASHBOARD AGENTE
    // ====================
    
    /**
     * GET /api/immobili/dashboard/all
     * Recupera tutti gli immobili con dettagli completi per la dashboard agente
     * Include: proprietario, agente assegnato, richiesta, contratto, valutazione, superficie
     */
    @GetMapping("/dashboard/all")
    public ResponseEntity<List<ImmobileListDTO>> getAllImmobiliWithDetails() {
        try {
            List<ImmobileListDTO> immobili = immobiliService.getAllImmobiliWithDetails();
            return ResponseEntity.ok(immobili);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * GET /api/immobili/dashboard/{id}
     * Recupera i dettagli completi di un immobile specifico
     */
    @GetMapping("/dashboard/{id}")
    public ResponseEntity<ImmobileListDTO> getImmobileDetailsById(@org.springframework.web.bind.annotation.PathVariable("id") Integer id) {
        try {
            ImmobileListDTO immobile = immobiliService.getImmobileDetailsById(id);
            return ResponseEntity.ok(immobile);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * POST /api/immobili/{idImmobile}/immagini
     * Carica una o più immagini per un immobile specifico.
     * Le immagini vengono salvate fisicamente in uploads/immagini/
     * e i metadati vengono registrati nel database.
     * 
     * @param idImmobile ID dell'immobile
     * @param files Array di file immagine da caricare
     * @return Lista di Immagine salvate nel database
     */
    @PostMapping("/{idImmobile}/immagini")
    public ResponseEntity<?> uploadImmagini(
            @PathVariable Integer idImmobile,
            @RequestParam("files") MultipartFile[] files) {
        
        try {
            // Verifica che l'immobile esista
            Immobile immobile = immobiliService.getImmobileById(idImmobile);
            if (immobile == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Immobile non trovato con ID: " + idImmobile);
            }
            
            // Verifica che ci siano file
            if (files == null || files.length == 0) {
                return ResponseEntity.badRequest().body("Nessun file fornito");
            }
            
            // Crea la directory se non esiste
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            List<Immagine> immaginiSalvate = new ArrayList<>();
            int ordinamento = immagineService.getImmaginiByImmobileId(idImmobile).size();
            
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                
                // Valida che sia un'immagine
                String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest()
                        .body("File non valido: " + originalFilename + ". Solo immagini consentite.");
                }
                
                // Genera nome file unico
                String extension = "";
                int dotIndex = originalFilename.lastIndexOf('.');
                if (dotIndex > 0) {
                    extension = originalFilename.substring(dotIndex);
                }
                String uniqueFileName = System.currentTimeMillis() + "_" + idImmobile + extension;
                
                // Salva il file fisicamente
                Path targetPath = uploadPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                
                // Calcola dimensione in KB
                Integer dimensioneKb = (int) (file.getSize() / 1024);
                
                // Crea record nel database
                Immagine immagine = new Immagine();
                immagine.setImmobile(immobile);
                immagine.setUrl("/uploads/immagini/" + uniqueFileName);
                immagine.setNomeFile(originalFilename);
                immagine.setDimensioneKb(dimensioneKb);
                immagine.setOrdinamento(ordinamento++);
                immagine.setCopertina(immaginiSalvate.isEmpty()); // Prima immagine = copertina
                
                Immagine salvata = immagineService.salvaImmagine(immagine);
                immaginiSalvate.add(salvata);
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(immaginiSalvate);
            
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Errore durante il salvataggio dei file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Errore imprevisto: " + e.getMessage());
        }
    }
}
