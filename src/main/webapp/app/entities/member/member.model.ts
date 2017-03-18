import { MemOrder } from '../mem-order';
import { Grade } from '../grade';
import { MemberCate } from '../member-cate';
export class Member {
    constructor(
        public id?: number,
        public cardNo?: string,
        public memName?: string,
        public password?: string,
        public phone?: string,
        public status?: number,
        public birthday?: any,
        public balance?: number,
        public bonus?: number,
        public remark?: string,
        public extfield1?: string,
        public extfield2?: string,
        public extfield3?: string,
        public extfield4?: string,
        public extfield5?: string,
        public order?: MemOrder,
        public grade?: Grade,
        public category?: MemberCate,
    ) {
    }
}
