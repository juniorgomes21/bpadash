package br.com.bpadash.services.treatment;

import br.com.bpadash.dto.treatment.RuleReplaceCustomDTO;
import br.com.bpadash.dto.treatment.RuleTreatmentPaDTO;
import br.com.bpadash.model.Bpa;
import br.com.bpadash.model.Bpac;
import br.com.bpadash.model.Bpai;
import br.com.bpadash.model.User;
import br.com.bpadash.model.treatment.RuleReplacementCustom;
import br.com.bpadash.model.treatment.TreatmentFile;
import br.com.bpadash.params.treatment.ParamTreatmentReplaceCustom;
import br.com.bpadash.params.treatment.ParamUpdateExecuteFile;
import br.com.bpadash.repository.treatment.RuleReplacementCustomRepository;
import br.com.bpadash.services.EncryptionService;
import br.com.bpadash.services.bpa.BpacService;
import br.com.bpadash.services.bpa.BpaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

@Service
public class RuleTreatmentReplaceCustomService {

    @Autowired
    private RuleReplacementCustomRepository ruleReplacementCustomRepository;


    public static List<RuleReplaceCustomDTO> dto(TreatmentFile treatmentFile) {
        List<RuleReplaceCustomDTO> ruleTreatmentPaDTOS = new ArrayList<>();

        treatmentFile.getRuleReplacementCustoms().forEach( rule -> {
            ruleTreatmentPaDTOS.add(new RuleReplaceCustomDTO(rule));
        });

        Collections.reverse(ruleTreatmentPaDTOS);

        return ruleTreatmentPaDTOS;
    }

    public RuleReplacementCustom get(Long id) {
        return ruleReplacementCustomRepository.findById(id).get();
    }

    public String isValidParans(ParamTreatmentReplaceCustom paramTreatmentReplaceCustom , User user , boolean edit) {
        String field = paramTreatmentReplaceCustom.getField();
        String fieldOne = paramTreatmentReplaceCustom.getCriterionOne();
        String fieldTwo = paramTreatmentReplaceCustom.getCriterionTwo().equals("") ? "fieldTwo" : paramTreatmentReplaceCustom.getCriterionTwo();
        String fieldThree = paramTreatmentReplaceCustom.getCriterionThree().equals("") ? "fieldThree" : paramTreatmentReplaceCustom.getCriterionThree();

        boolean isEquals = fieldOne.equals(fieldTwo) || fieldOne.equals(fieldThree) || fieldTwo.equals(fieldThree);

        if(isEquals) {
            return "PARANS EQUALS";
        }

        boolean isFieldEquals = paramTreatmentReplaceCustom.getField().equals(paramTreatmentReplaceCustom.getCriterionOne());
        boolean isValueEquals = paramTreatmentReplaceCustom.getNewValueField().equals(paramTreatmentReplaceCustom.getValueCriterionOne());

        if(isValueEquals && isFieldEquals) {
            return "VALUES EQUALS";
        }

        if(!edit && user.getTreatmentFile().getCount() == 0) {
            return "REACHED MAX LENGTH";
        }


        List<RuleReplacementCustom> ruleList = user.getTreatmentFile().getRuleReplacementCustoms().stream().filter(rule -> rule.getField().equals(field)).toList();

        if(!ruleList.isEmpty()) {
            String fieldParam = paramTreatmentReplaceCustom.getNewValueField();
            String fielValuedOne = paramTreatmentReplaceCustom.getValueCriterionOne();
            String fieldValueTwo = paramTreatmentReplaceCustom.getValueCriterionTwo().equals("") ? "" : paramTreatmentReplaceCustom.getValueCriterionTwo();
            String fieldValueThree = paramTreatmentReplaceCustom.getValueCriterionThree().equals("") ? "" : paramTreatmentReplaceCustom.getValueCriterionThree();



            for(RuleReplacementCustom rule: ruleList) {

                boolean condition;
                boolean conditionOne;
                boolean conditionTwo;
                boolean conditionThree;

                condition = rule.getNewValueField().equals(fieldParam);
                conditionOne = rule.getValueCriterionOne().equals(fielValuedOne);
                conditionTwo = rule.getValueCriterionTwo().equals(fieldValueTwo);
                conditionThree = rule.getValueCriterionThree().equals(fieldValueThree);

                if(condition && conditionOne && conditionTwo && conditionThree) {
                    return "EXIST RULE";
                }
            }
        }

        return "OK";
    }

    public void updateAndSave(RuleReplacementCustom ruleReplacementCustom , ParamUpdateExecuteFile paramUpdateExecuteFile) {
        ruleReplacementCustom.setExecuteBpac(paramUpdateExecuteFile.getExecuteBpac());
        ruleReplacementCustom.setExecuteBpai(paramUpdateExecuteFile.getExecuteBpai());

        this.save(ruleReplacementCustom);
    }

    public RuleReplacementCustom create(ParamTreatmentReplaceCustom paramTreatmentReplaceCustom) {
        return new RuleReplacementCustom(paramTreatmentReplaceCustom);
    }

    public RuleReplacementCustom save(RuleReplacementCustom ruleReplacementCustom) {
        return ruleReplacementCustomRepository.save(ruleReplacementCustom);
    }

    @Transactional
    public void delete(Long id) {
        ruleReplacementCustomRepository.deleteById(id);
    }

    public void executeBpac(RuleReplacementCustom ruleReplacementCustom, List<Bpac> bpacList, List<Bpac> bpacModify) {

        Class<Bpac> classeBpac = Bpac.class;
        for(Bpac bpac: bpacList) {
            String field = ruleReplacementCustom.getField();
            String criterionOne = ruleReplacementCustom.getCriterionOne();
            String criterionTwo = ruleReplacementCustom.getCriterionTwo();
            String criterionThree = ruleReplacementCustom.getCriterionThree();

            try {
                if(!criterionOne.equals("")) {

                    Field campo = classeBpac.getDeclaredField(criterionOne);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpac);

                    boolean equalsCriterionOne = valorDoCampo.equals(ruleReplacementCustom.getValueCriterionOne());

                    if (!equalsCriterionOne) continue;
                }

                if(!criterionTwo.equals("")) {

                    Field campo = classeBpac.getDeclaredField(criterionTwo);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpac);

                    boolean equalsCriterionTwo = valorDoCampo.equals(ruleReplacementCustom.getValueCriterionTwo());

                    if (!equalsCriterionTwo) continue;
                }

                if(!criterionThree.equals("")) {

                    Field campo = classeBpac.getDeclaredField(criterionThree);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpac);

                    boolean equalsCriterionThree = valorDoCampo.equals(ruleReplacementCustom.getValueCriterionThree());

                    if (!equalsCriterionThree) continue;
                }

                Field campo = classeBpac.getDeclaredField(field);
                campo.setAccessible(true);
                campo.set(bpac, ruleReplacementCustom.getNewValueField());

                bpacModify.add(bpac);
            } catch (NoSuchFieldException | IllegalAccessException  e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void executeBpai(RuleReplacementCustom rule, List<Bpai> bpaiList, List<Bpai> bpaiModify, boolean all) {
        List<String> values = new ArrayList<>(List.of("cnspac", "cid", "Nmpac", "dtnasc", "idade", "sexo", "raca"));

        String field = rule.getField();
        String criterionOne = rule.getCriterionOne();
        String criterionTwo = rule.getCriterionTwo();
        String criterionThree = rule.getCriterionThree();

        boolean fieldB = false;
        boolean criterionOneB = false;
        boolean criterionTwoB = false;
        boolean criterionThreeB = false;

        if(!all) {
            fieldB = values.contains(rule.getField());
            criterionOneB = values.contains(rule.getCriterionOne());
            criterionTwoB = values.contains(rule.getCriterionTwo());
            criterionThreeB = values.contains(rule.getCriterionThree());

            if(fieldB || criterionOneB || criterionTwoB || criterionThreeB) {
                EncryptionService.decryptBpaiForTreatment(bpaiList);
            }
        }

        Class<Bpai> classeBpai = Bpai.class;
        for(Bpai bpai: bpaiList) {


            try {
                if(!criterionOne.equals("")) {

                    Field campo = classeBpai.getDeclaredField(criterionOne);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpai);

                    boolean equalsCriterionOne = valorDoCampo.equals(rule.getValueCriterionOne());

                    if (!equalsCriterionOne) continue;
                }

                if(!criterionTwo.equals("")) {

                    Field campo = classeBpai.getDeclaredField(criterionTwo);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpai);

                    boolean equalsCriterionTwo = valorDoCampo.equals(rule.getValueCriterionTwo());

                    if (!equalsCriterionTwo) continue;
                }

                if(!criterionThree.equals("")) {

                    Field campo = classeBpai.getDeclaredField(criterionThree);

                    // Tornando o campo acessível (pode ser necessário se o campo for privado)
                    campo.setAccessible(true);

                    // Obtendo o valor do campo no objeto 'bpac'
                    Object valorDoCampo = campo.get(bpai);

                    boolean equalsCriterionThree = valorDoCampo.equals(rule.getValueCriterionThree());

                    if (!equalsCriterionThree) continue;
                }

                Field campo = classeBpai.getDeclaredField(field);
                campo.setAccessible(true);
                campo.set(bpai, rule.getNewValueField());

                bpaiModify.add(bpai);
            } catch (NoSuchFieldException | IllegalAccessException  e) {
                e.printStackTrace();
                break;
            }
        }

        if(!all) {
            if(fieldB || criterionOneB || criterionTwoB || criterionThreeB) {
                EncryptionService.encryptBpaiForTreatment(bpaiList);
            }
        }
    }
}
