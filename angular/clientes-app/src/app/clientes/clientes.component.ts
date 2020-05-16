import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { tap } from "rxjs/operators";

import { Cliente }  from './cliente';
import { ClienteService } from './cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = []

  constructor(
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.clienteService.getClientes().pipe(
      tap(
        clientes => {
          this.clientes = clientes;
          console.log('ClientesComponent: tap 3');
          clientes.forEach(
            cliente=>{
              console.log(cliente.nombre);
            }
          )
        }
      )
    ).subscribe(
//      clientes => this.clientes = clientes
    );
  }

  delete(cliente: Cliente): void{
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
      buttonsStyling: false
    })

    swalWithBootstrapButtons.fire({
      title: '¿Está seguro?',
      text: `¿Seguro de que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      reverseButtons: true
    }).then((result) => {
      if (result.value) {
        this.clienteService.delete(cliente.id).subscribe(
          () => {
            this.clientes=this.clientes.filter(cli => cli !== cliente);
            swalWithBootstrapButtons.fire(
              'Eliminado!',
              `El cliente ${cliente.nombre} fué eliminado.`,
              'success'
            )
          }
        );
      }
    })
  }

}
