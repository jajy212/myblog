import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JblogGoodsModule } from './goods/goods.module';
import { JblogGoodsTypeModule } from './goods-type/goods-type.module';
import { JblogGradeModule } from './grade/grade.module';
import { JblogMemberModule } from './member/member.module';
import { JblogMemberCateModule } from './member-cate/member-cate.module';
import { JblogMemOrderModule } from './mem-order/mem-order.module';
import { JblogPayModule } from './pay/pay.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JblogGoodsModule,
        JblogGoodsTypeModule,
        JblogGradeModule,
        JblogMemberModule,
        JblogMemberCateModule,
        JblogMemOrderModule,
        JblogPayModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogEntityModule {}
