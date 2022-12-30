package gmarques.remotewincontrol.presenter.ui.mousepad.eventos

/** um evento de clique com duraçao maior que esse valor sera desconsiderado */
const val CLICK_INTERVAL = 150

/** um evento de clique de dois dedos com duraçao maior que esse valor sera desconsiderado */
const val CLICK_INTERVAL_TWO_FINGERS = 180

/** um evento de clique longo com duraçao menor que esse valor sera desconsiderado */
const val LONG_CLICK_DELAY = CLICK_INTERVAL + 1

/** Se o dedo do usuario se mover mais que esse valor durante um evento de clique o evento sera desconsiderado */
const val MAX_MOV_APR = 20

/** Se os dedos do usuario se moverem mais que esse valor durante um evento de clique com dois dedos
 *  o evento sera desconsiderado */
const val MAX_MOV_APR_TWO_FINGERS = 500