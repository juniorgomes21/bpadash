package br.com.bpadash.services.user;

import br.com.bpadash.dto.bpa.BpaiDTO;
import br.com.bpadash.dto.user.StockHistoryDTO;
import br.com.bpadash.model.bpa.Bpai;
import br.com.bpadash.model.user.Employee;
import br.com.bpadash.model.user.StockHistory;
import br.com.bpadash.model.user.User;
import br.com.bpadash.repository.user.StockHistoryRepository;
import br.com.bpadash.services.cryptography.EnCryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHistoryService {

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    public Page<StockHistoryDTO> get(User user, Pageable pageable) {
        Page<StockHistory> page = stockHistoryRepository.findByUser(user, pageable);

        List<StockHistory> stockHistoryList = page.getContent();

        List<StockHistoryDTO> stockHistoryDTOList = stockHistoryList.stream()
                .map(StockHistoryDTO::new)
                .collect(Collectors.toList());

        return new PageImpl<>(stockHistoryDTOList, pageable, page.getTotalElements());
    }

    public List<StockHistory> get(Employee employee) {
        return stockHistoryRepository.findByEmployee(employee);
    }

    public StockHistory register(String action, String type, LocalDate dateFile, int linesModified, User user, Employee employee) {

        return this.save(new StockHistory(action, type, dateFile, linesModified , user, employee));
    }

    public StockHistory save(StockHistory stockHistory) {
        return stockHistoryRepository.save(stockHistory);
    }
}
