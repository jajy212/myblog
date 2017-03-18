import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JblogSharedModule } from '../../shared';

import {
    GoodsTypeService,
    GoodsTypePopupService,
    GoodsTypeComponent,
    GoodsTypeDetailComponent,
    GoodsTypeDialogComponent,
    GoodsTypePopupComponent,
    GoodsTypeDeletePopupComponent,
    GoodsTypeDeleteDialogComponent,
    goodsTypeRoute,
    goodsTypePopupRoute,
} from './';

let ENTITY_STATES = [
    ...goodsTypeRoute,
    ...goodsTypePopupRoute,
];

@NgModule({
    imports: [
        JblogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GoodsTypeComponent,
        GoodsTypeDetailComponent,
        GoodsTypeDialogComponent,
        GoodsTypeDeleteDialogComponent,
        GoodsTypePopupComponent,
        GoodsTypeDeletePopupComponent,
    ],
    entryComponents: [
        GoodsTypeComponent,
        GoodsTypeDialogComponent,
        GoodsTypePopupComponent,
        GoodsTypeDeleteDialogComponent,
        GoodsTypeDeletePopupComponent,
    ],
    providers: [
        GoodsTypeService,
        GoodsTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogGoodsTypeModule {}
