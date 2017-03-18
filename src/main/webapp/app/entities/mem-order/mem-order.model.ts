import { Pay } from '../pay';
import { Goods } from '../goods';
import { Member } from '../member';
export class MemOrder {
    constructor(
        public id?: number,
        public orderCode?: string,
        public orderType?: string,
        public createdDate?: any,
        public status?: number,
        public lastModifiedDate?: any,
        public remark?: string,
        public extfield1?: string,
        public extfield2?: string,
        public extfield3?: string,
        public extfield4?: string,
        public extfield5?: string,
        public pay?: Pay,
        public goods?: Goods,
        public createdBy?: Member,
    ) {
    }
}
