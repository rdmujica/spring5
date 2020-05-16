import { Router } from '@angular/router'
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators'
import Swal from 'sweetalert2';

import { CLIENTES } from './cliente.json';
import { Cliente } from './cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private urlEndpoint:string='http://localhost:8080/api/clientes';

  private httHeaders = new HttpHeaders({'Content-Type':'application/json'});

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  getClientes(): Observable<Cliente[]> {
    //return of(CLIENTES);
    return this.http.get<Cliente[]> (this.urlEndpoint);
    //return this.http.get(this.urlEndpoint).pipe(map((response)=>response as Cliente[]));
  }

  create(cliente: Cliente): Observable<Cliente>{
    return this.http.post(this.urlEndpoint,cliente,{headers: this.httHeaders}).pipe(
      map((response: any) => response.cliente as Cliente),
      catchError( (e) => {

        if(e.status==400){
            return throwError(e);
        }

        console.error(e.error.mensaje);
        Swal.fire('Error al crear al cliente',e.error.mensaje, 'error');
        return throwError(e);
      }

      )
    );
  }

  getCliente(id: number):Observable<Cliente>{
   return this.http.get<Cliente>(`${this.urlEndpoint}/${id}`,{headers: this.httHeaders}).pipe(
     catchError( (e) => {
       this.router.navigate(['/clientes']);
       console.error(e.error.mensaje);
       Swal.fire('Error al editar',e.error.mensaje, 'error');
       return throwError(e);
     })
   );
  }

  update(cliente: Cliente): Observable<any>{
    return this.http.put<any>(`${this.urlEndpoint}/${cliente.id}`,cliente,{headers: this.httHeaders}).pipe(
      catchError( e => {

        if(e.status==400){
            return throwError(e);
        }

        console.error(e.error.mensaje);
        Swal.fire('Error al crear al actualizar cliente',e.error.mensaje, 'error');
        return throwError(e);
      }
      )
    );
  }
  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlEndpoint}/${id}`,{headers: this.httHeaders}).pipe(
      catchError( e => {
        console.error(e.error.mensaje);
        Swal.fire('Error al crear al eliminar cliente',e.error.mensaje, 'error');
        return throwError(e);
      }
      )
    );
  }
}
