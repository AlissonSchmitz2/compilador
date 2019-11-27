package br.com.compilador.analisadores;

public enum TabelaParsingDerivacoes {

	DERIVACOES_1(getArrayString("PROGRAM|IDENTIFICADOR|;|BLOCO|.")),
	DERIVACOES_2(getArrayString("DCLROT|DCLCONST|DCLVAR|DCLPROC|CORPO")),
	DERIVACOES_3(getArrayString("LABEL|LID|;")),
	DERIVACOES_4(getArrayString("IDENTIFICADOR|REPIDENT")),
	DERIVACOES_5(getArrayString(",|IDENTIFICADOR|REPIDENT")),
	DERIVACOES_6(getArrayString("CONST|IDENTIFICADOR|=|INTEIRO|;|LDCONST")),
	DERIVACOES_7(getArrayString("IDENTIFICADOR|=|INTEIRO|;|LDCONST")),
	DERIVACOES_8(getArrayString("VAR|LID|:|TIPO|;|LDVAR")),
	DERIVACOES_9(getArrayString("LID|:|TIPO|;|LDVAR")),
	DERIVACOES_10(getArrayString("INTEGER")),
	DERIVACOES_11(getArrayString("ARRAY|[|INTEIRO|..|INTEIRO|]|OF|INTEGER")),
	DERIVACOES_12(getArrayString("PROCEDURE|IDENTIFICADOR|DEFPAR|;|BLOCO|;|DCLPROC")),
	DERIVACOES_13(getArrayString("(|LID|:|INTEGER|)")),
	DERIVACOES_14(getArrayString("BEGIN|COMANDO|REPCOMANDO|END")),
	DERIVACOES_15(getArrayString(";|COMANDO|REPCOMANDO")),
	DERIVACOES_16(getArrayString("CORPO")),
	DERIVACOES_17(getArrayString("CALL|IDENTIFICADOR|PARAMETROS")),
	DERIVACOES_18(getArrayString("GOTO|IDENTIFICADOR")),
	DERIVACOES_19(getArrayString("IF|EXPRESSAO|THEN|COMANDO|ELSEPARTE")),
	DERIVACOES_20(getArrayString("WHILE|EXPRESSAO|DO|COMANDO")),
	DERIVACOES_21(getArrayString("REPEAT|COMANDO|UNTIL|EXPRESSAO")),
	DERIVACOES_22(getArrayString("READLN|(|VARIAVEL|REPVARIAVEL|)")),
	DERIVACOES_23(getArrayString("WRITELN|(|ITEMSAIDA|REPITEM|)")),
	DERIVACOES_24(getArrayString("IDENTIFICADOR|RCOMID")),
	DERIVACOES_25(getArrayString("FOR|IDENTIFICADOR|:=|EXPRESSAO|TO|EXPRESSAO|DO|COMANDO")),
	DERIVACOES_26(getArrayString("CASE|EXPRESSAO|OF|CONDCASE|END")),
	DERIVACOES_27(getArrayString("RVAR|:=|EXPRESSAO")),
	DERIVACOES_28(getArrayString(":|COMANDO")),
	DERIVACOES_29(getArrayString("[|EXPRESSAO|]")),
	DERIVACOES_30(getArrayString("(|EXPRESSAO|REPPAR|)")),
	DERIVACOES_31(getArrayString(",|EXPRESSAO|REPPAR")),
	DERIVACOES_32(getArrayString("ELSE|COMANDO")),
	DERIVACOES_33(getArrayString("IDENTIFICADOR|VARIAVEL1")),
	DERIVACOES_34(getArrayString("[|EXPRESSAO|]")),
	DERIVACOES_35(getArrayString(",|VARIAVEL|REPVARIAVEL")),
	DERIVACOES_36(getArrayString("EXPRESSAO")),
	DERIVACOES_37(getArrayString("LITERAL")),
	DERIVACOES_38(getArrayString(",|ITEMSAIDA|REPITEM")),
	DERIVACOES_39(getArrayString("EXPSIMP|REPEXPSIMP")),
	DERIVACOES_40(getArrayString("=|EXPSIMP")),
	DERIVACOES_41(getArrayString(">|EXPSIMP")),
	DERIVACOES_42(getArrayString(">=|EXPSIMP")),
	DERIVACOES_43(getArrayString("<|EXPSIMP")),
	DERIVACOES_44(getArrayString("<=|EXPSIMP")),
	DERIVACOES_45(getArrayString("<>|EXPSIMP")),
	DERIVACOES_46(getArrayString("TERMO|REPEXP")),
	DERIVACOES_47(getArrayString("+|TERMO|REPEXP")),
	DERIVACOES_48(getArrayString("-|TERMO|REPEXP")),
	DERIVACOES_49(getArrayString("OR|TERMO|REPEXP")),
	DERIVACOES_50(getArrayString("+|TERMO|REPEXP")),
	DERIVACOES_51(getArrayString("-|TERMO|REPEXP")),
	DERIVACOES_52(getArrayString("FATOR|REPTERMO")),
	DERIVACOES_53(getArrayString("AND|FATOR|REPTERMO")),
	DERIVACOES_54(getArrayString("*|FATOR|REPTERMO")),
	DERIVACOES_55(getArrayString("/|FATOR|REPTERMO")),
	DERIVACOES_56(getArrayString("NOT|FATOR")),
	DERIVACOES_57(getArrayString("VARIAVEL")),
	DERIVACOES_58(getArrayString("INTEIRO")),
	DERIVACOES_59(getArrayString("(|EXPRESSAO|)")),
	DERIVACOES_60(getArrayString("INTEIRO|RPINTEIRO|:|COMANDO|CONTCASE")),
	DERIVACOES_61(getArrayString(";|CONDCASE")),
	DERIVACOES_62(getArrayString(",|INTEIRO|RPINTEIRO")),
	DERIVACOES_63(getArrayString("NULL"));
	
    private final String[] derivacoes;
	
    private TabelaParsingDerivacoes(String[] derivacoes) {
        this.derivacoes = derivacoes;
    }
    
    public String[] getDerivacoes() {
        return derivacoes;
    }
    
	public static String[] getArrayString(String string) {		
		return string.replace("|", "!").split("!");
	}
}
