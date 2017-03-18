
const enum PayType {
    'CASH',
    'ALIPAY',
    'WEIXIN',
    'BANKCARD'

};
import { MemOrder } from '../mem-order';
export class Pay {
    constructor(
        public id?: number,
        public amount?: number,
        public payDate?: any,
        public paytype?: PayType,
        public memOrder?: MemOrder,
    ) {
    }
}
