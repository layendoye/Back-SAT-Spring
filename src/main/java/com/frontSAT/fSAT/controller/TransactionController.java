package com.frontSAT.fSAT.controller;


import com.frontSAT.fSAT.form.TransactionForm;
import com.frontSAT.fSAT.model.*;
import com.frontSAT.fSAT.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class TransactionController {

    @Autowired
    TarifService tarifService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserCompteActuelService userCompteActuelService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    CompteService compteService;

    @PostMapping(value = "/transation/envoie")
    @PreAuthorize("hasAnyAuthority('ROLE_utilisateur','ROLE_admin','ROLE_admin_Principal')")
    public void envois(@RequestBody TransactionForm envoisForm) throws Exception {
        Transaction envois =new Transaction();
        envois.setNomClientEmetteur(envoisForm.getNomClientEmetteur());
        envois.setTelephoneEmetteur(envoisForm.getTelephoneEmetteur());
        envois.setNciEmetteur(envoisForm.getNciEmetteur());
        envois.setMontant(envoisForm.getMontant());
        envois.setNomClientRecepteur(envoisForm.getNomClientRecepteur());
        envois.setTelephoneRecepteur(envoisForm.getTelephoneRecepteur());
        long montant =envoisForm.getMontant();
        int frais=0;
        List<Tarifs> tarifs  = tarifService.findAll();
        for(int i=0; i< tarifs.size();i++){
            long borneeInf= tarifs.get(i).getBorneInferieure();
            long borneSup=tarifs.get(i).getBorneSuperieure();

            if(borneeInf<=montant && montant<=borneSup){
                if(2000001<=montant && montant<=3000000){
                    frais=(int)(montant*0.02); break;
                }else{
                    frais=tarifs.get(i).getValeur(); break;
                }
            }
        }
        long commissionEmetteur=(long)(frais*0.2);
        long commissionSAT=(long)(frais*0.4);
        long taxesEtat=(long)(frais*0.3);
        User userConnecte=userDetailsService.getUserConnecte();
        List<UserCompteActuel> userCompteActuels=userCompteActuelService.findUserCompteActuelByUser(userConnecte);
        if(userCompteActuels.size()==0){
            throw new Exception("L'utilisateur n'est affecté à aucun compte ");
        }
        UserCompteActuel userCompte=userCompteActuels.get(userCompteActuels.size()-1);
        if(userCompte.getCompte().getSolde()<montant){
            throw new Exception("Le solde de votre compte ne vous permet pas de traiter cette transaction !");
        }
        SimpleDateFormat formater = new SimpleDateFormat("ssmm ddhh yyMM");
        Date now=new Date();
        String code=formater.format(now);
        envois.setCode(code);
        envois.setDateEnvoi(now);
        envois.setFrais(frais);
        envois.setCommissionEmetteur(commissionEmetteur);
        envois.setCommissionWari(commissionSAT);
        envois.setTaxesEtat(taxesEtat);
        envois.setUserComptePartenaireEmetteur(userCompte);
        envois.setStatus("Envoyer");
        transactionService.save(envois);

        Compte compteSAT=compteService.findByNumeroCompte("1910 1409 0043").orElseThrow();
        compteSAT.setSolde(compteSAT.getSolde()+commissionSAT);
        compteService.save(compteSAT);

        Compte compteEtat=compteService.findByNumeroCompte("0221 0445 0443").orElseThrow();
        compteEtat.setSolde(compteEtat.getSolde()+taxesEtat);
        compteService.save(compteEtat);

        Compte compteEmet=userCompte.getCompte();
        compteEmet.setSolde(compteEmet.getSolde()+commissionEmetteur);
        compteService.save(compteEmet);

    }

    /*
    public function recuDeTransaction(String type,Transaction transaction){
        $senegal='Sénégal';
        $tel='Telephone';
        if($type=='envoi'){
            $libelle="Reçu d'envoi";
            $guichetier=$transaction->getUserComptePartenaireEmetteur()->getUtilisateur();
            $entreprise= $guichetier->getEntreprise();
            $date=$transaction->getDateEnvoi();
            $envoyeur=[
            'Nom'=>$transaction->getNomClientEmetteur(),
                    'Pays'=>$senegal,
                    $tel=>$transaction->getTelephoneEmetteur(),
                    'NCI'=>$transaction->getNciEmetteur()
            ];
            $beneficiaire=[
            'Nom'=>$transaction->getNomClientRecepteur(),
                    'Pays'=>$senegal,
                    $tel=>$transaction->getTelephoneRecepteur(),
            ];
            $trans=[
            'CodeTransaction'=>$transaction->getCode(),
                    'MontantEnvoye'=>$this->formatMil($transaction->getMontant()).' CFA',
                    'CommissionsTTC'=>$this->formatMil($transaction->getFrais()).' CFA',
                    'Total'=>$this->formatMil($transaction->getMontant()+$transaction->getFrais()).' CFA'
            ];
        }
        else{
            $libelle="Reçu de retrait";
            $guichetier=$transaction->getUserComptePartenaireRecepteur()->getUtilisateur();
            $entreprise= $guichetier->getEntreprise();
            $date=new \DateTime();
            $envoyeur=[
            'Nom'=>$transaction->getNomClientEmetteur(),
                    'Pays'=>$senegal,
                    $tel=>$transaction->getTelephoneEmetteur(),
            ];
            $beneficiaire=[
            'Nom'=>$transaction->getNomClientRecepteur(),
                    'Pays'=>$senegal,
                    $tel=>$transaction->getTelephoneRecepteur(),
                    'NCI'=>$transaction->getNciRecepteur()
            ];
            $trans=[
            'CodeTransaction'=>$transaction->getCode(),
                    'MontantRetire'=> $this->formatMil($transaction->getMontant()).' CFA',
            ];
        }
        return [
        'Type'=>$libelle,
                'Entreprise'=>[
        'RaisonSociale'=>$entreprise->getRaisonSociale(),
                'Adresse'=>$entreprise->getAdresse(),
                $tel=>$entreprise->getTelephoneEntreprise(),
                'Guichetier'=>$guichetier->getNom(),
                'Date'=> $date->format('d-m-Y H:i')
            ],
        'Envoyeur'=> $envoyeur,
                'Beneficiaire'=> $beneficiaire,
                'Transaction'=>$trans
        ];
    }*/

}
