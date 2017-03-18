import { GoodsType } from '../goods-type';
export class Goods {
    constructor(
        public id?: number,
        public goodsName?: string,
        public serialNo?: string,
        public briefCode?: string,
        public barCode?: string,
        public unit?: string,
        public isService?: string,
        public remark?: string,
        public extfield1?: string,
        public extfield2?: string,
        public extfield3?: string,
        public extfield4?: string,
        public extfield5?: string,
        public goodsType?: GoodsType,
    ) {
    }
}
