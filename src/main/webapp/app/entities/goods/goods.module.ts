import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JblogSharedModule } from '../../shared';

import {
    GoodsService,
    GoodsPopupService,
    GoodsComponent,
    GoodsDetailComponent,
    GoodsDialogComponent,
    GoodsPopupComponent,
    GoodsDeletePopupComponent,
    GoodsDeleteDialogComponent,
    goodsRoute,
    goodsPopupRoute,
} from './';

let ENTITY_STATES = [
    ...goodsRoute,
    ...goodsPopupRoute,
];

@NgModule({
    imports: [
        JblogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        GoodsComponent,
        GoodsDetailComponent,
        GoodsDialogComponent,
        GoodsDeleteDialogComponent,
        GoodsPopupComponent,
        GoodsDeletePopupComponent,
    ],
    entryComponents: [
        GoodsComponent,
        GoodsDialogComponent,
        GoodsPopupComponent,
        GoodsDeleteDialogComponent,
        GoodsDeletePopupComponent,
    ],
    providers: [
        GoodsService,
        GoodsPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JblogGoodsModule {}
