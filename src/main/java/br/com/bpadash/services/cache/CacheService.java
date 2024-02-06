package br.com.bpadash.services.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private CacheManager cacheManager;


    @CacheEvict(allEntries = true, key = "#usuarioId", value = {
            "sexGraphics"
    })
    public void evictAll(String usuarioId) {
        // Este método será chamado para limpar todos os caches associados à chave usuarioId
    }
}
