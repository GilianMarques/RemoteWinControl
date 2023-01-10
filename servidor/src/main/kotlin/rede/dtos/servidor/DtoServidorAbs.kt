package rede.dtos.servidor

abstract class DtoServidorAbs {

    abstract val tipo: TIPO_DTO_SERVIDOR


    abstract fun toJson(): String

}