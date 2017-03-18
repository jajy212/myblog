import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { GoodsTypeDetailComponent } from '../../../../../../main/webapp/app/entities/goods-type/goods-type-detail.component';
import { GoodsTypeService } from '../../../../../../main/webapp/app/entities/goods-type/goods-type.service';
import { GoodsType } from '../../../../../../main/webapp/app/entities/goods-type/goods-type.model';

describe('Component Tests', () => {

    describe('GoodsType Management Detail Component', () => {
        let comp: GoodsTypeDetailComponent;
        let fixture: ComponentFixture<GoodsTypeDetailComponent>;
        let service: GoodsTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [GoodsTypeDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    GoodsTypeService
                ]
            }).overrideComponent(GoodsTypeDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GoodsTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GoodsTypeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new GoodsType(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.goodsType).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
