package com.plutus.kotak.commonlibs.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class TransactionEntity {
    @Id
    private String transactionId;
    private String foracid;  // FORACID from PlutusFinacleData
    private String acctName; // ACCT_NAME from PlutusFinacleData
    private String lastTranDateCr; // LAST_TRAN_DATE_CR from PlutusFinacleData
    private String tranDate; // TRAN_DATE from PlutusFinacleData
    private String tranId; // TRAN_ID from PlutusFinacleData
    private String partTranSrlNum; // PART_TRAN_SRL_NUM from PlutusFinacleData
    private String delFlg; // DEL_FLG from PlutusFinacleData
    private String tranType; // TRAN_TYPE from PlutusFinacleData
    private String tranSubType; // TRAN_SUB_TYPE from PlutusFinacleData
    private String partTranType; // PART_TRAN_TYPE from PlutusFinacleData
    private String glSubHeadCode; // GL_SUB_HEAD_CODE from PlutusFinacleData
    private String acid; // ACID from PlutusFinacleData
    private String valueDate; // VALUE_DATE from PlutusFinacleData
    private Double tranAmt; // TRAN_AMT from PlutusFinacleData
    private String tranParticular; // TRAN_PARTICULAR from PlutusFinacleData
    private String entryDate; // ENTRY_DATE from PlutusFinacleData
    private String pstdDate; // PSTD_DATE from PlutusFinacleData
    private String refNum; // REF_NUM from PlutusFinacleData
    private String instrmntType; // INSTRMNT_TYPE from PlutusFinacleData
    private String instrmntDate; // INSTRMNT_DATE from PlutusFinacleData
    private String instrmntNum; // INSTRMNT_NUM from PlutusFinacleData
    private String tranRmks; // TRAN_RMKS from PlutusFinacleData
    private String custId; // CUST_ID from PlutusFinacleData
    private String brCode; // BR_CODE from PlutusFinacleData
    private String crncyCode; // CRNCY_CODE from PlutusFinacleData
    private String tranCrncyCode; // TRAN_CRNCY_CODE from PlutusFinacleData
    private Double refAmt; // REF_AMT from PlutusFinacleData
    private String solId; // SOL_ID from PlutusFinacleData
    private String bankCode; // BANK_CODE from PlutusFinacleData
    private String treaRefNum; // TREA_REF_NUM from PlutusFinacleData
    private String reversalDate; // REVERSAL_DATE from PlutusFinacleData
    private String reversalValueDate; // REVERSAL_VALUE_DATE from PlutusFinacleData
    private String tranParticular2; // TRAN_PARTICULAR_2 from PlutusFinacleData
    private String tranParticularCode; // TRAN_PARTICULAR_CODE from PlutusFinacleData
    private String trStatus; // TR_STATUS from PlutusFinacleData
    private String partyCode; // PARTY_CODE from PlutusFinacleData
    private String glDate; // GL_DATE from PlutusFinacleData
    private String bankId; // BANK_ID from PlutusFinacleData
    private String tranFreeCode1; // TRAN_FREE_CODE1 from PlutusFinacleData
    private String tranFreeCode2; // TRAN_FREE_CODE2 from PlutusFinacleData
    private String reversalStatus; // REVERSAL_STATUS from PlutusFinacleData
    private Double availableAmt; // AVAILABLE_AMT from PlutusFinacleData
    private Double acctBalance; // ACCT_BALANCE from PlutusFinacleData
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 