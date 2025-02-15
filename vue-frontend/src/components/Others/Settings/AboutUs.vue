//Adapted By GRPTeam202205 (MIT license)
<template>
    <!-- This page shows all contributors(team members) for this project -->
    <section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
            <div class="col col-xl-10">
                <!-- Project Title -->
                <div class="card mb-5" style="border-radius: 15px;">
                    <div class="card-body p-4">
                        <h3 class="mb-3">{{$t('CloudWalker Inventory System')}}</h3>
                        <p class="small mb-0"><i class="far fa-star fa-lg"></i> <span class="mx-2">|</span> 
                            {{$t('Created by')}} <strong>{{$t('GRPTeam 202205')}}</strong> {{$t('on 6, April, 2023')}}
                        </p>
                        <hr class="my-4">
                        <div class="d-flex justify-content-start align-items-center">
                        <p class="mb-0 text-uppercase"><i class="fas fa-cog me-2"></i> <span
                            class="text-muted small">{{$t('Settings')}}</span></p>
                        <p class="mb-0 text-uppercase"><i class="fas fa-link ms-4 me-2"></i> <span
                            class="text-muted small">{{$t('Program Link')}}</span></p>
                        <p class="mb-0 text-uppercase"><i class="fas fa-ellipsis-h ms-4 me-2"></i> <span
                            class="text-muted small">{{$t('Program Satus')}}</span>
                            <span class="ms-3 me-4">|</span></p>
                        <a href="#!">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/avatar-2.webp" alt="avatar"
                            class="img-fluid rounded-circle me-1" width="35">
                        </a>
                        <a href="#!">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/avatar-3.webp" alt="avatar"
                            class="img-fluid rounded-circle me-1" width="35">
                        </a>
                        <a href="#!">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/avatar-4.webp" alt="avatar"
                            class="img-fluid rounded-circle me-1" width="35">
                        </a>
                        <a href="#!">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Avatars/avatar-5.webp" alt="avatar"
                            class="img-fluid rounded-circle me-3" width="35">
                        </a>
                        <button type="button" class="btn btn-outline-dark btn-sm btn-floating">
                            <i class="fas fa-plus"></i>
                        </button>
                        </div>                             
                                    
                        <div class="card-body">
                            <p class="mb-4"><span class="text-primary font-italic me-1">{{$t('Assignment')}}</span> {{$t('Project Status')}}
                            </p>
                            <p class="mb-1" style="font-size: .77rem;">{{$t('Web Design')}}</p>
                            <div class="progress rounded" style="height: 8px;">
                                <div class="progress-bar bg-success" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <p class="mt-4 mb-1" style="font-size: .77rem;">{{$t('Mobile Adaption')}}</p>
                            <div class="progress rounded" style="height: 8px;">
                                <div class="progress-bar" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <p class="mt-4 mb-1" style="font-size: .77rem;">{{$t('Backend API')}}</p>
                            <div class="progress rounded mb-2" style="height: 8px;">
                                <div class="progress-bar bg-warning" role="progressbar" style="width: 100%" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                            <p class="mt-4 mb-1" style="font-size: .77rem;">{{$t('Software Maintenance')}}</p>
                            <div class="progress rounded" style="height: 8px;">
                                <div class="progress-bar bg-info" role="progressbar" style="width: 100%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Team Members -->
                <div class="card" style="border-radius: 15px;">
                    <div class="card-body p-4">
                        <h3 class="mb-3">{{$t('Team Members')}}</h3>
                        <p class="small mb-0"><i class="fas fa-star fa-lg text-warning"></i> <span class="mx-2">|</span>
                        {{$t('Public')}} <span class="mx-2">|</span>{{$t('Updated by')}} <strong>{{$t('GRPTeam 202205')}}</strong> {{$t('on 6, April, 2023')}}
                        </p>
                        
                        <div class="container py-5">
                            <div class="row">
                            <div class="col-lg-4">
                                <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="/static/UserProfile/man3.png" alt="avatar"
                                    class="rounded-circle img-fluid" style="width: 110px;">
                                    <h5 class="my-3">{{$t('Bolin')}}</h5>
                                    <p class="text-muted mb-1">{{$t('Project Developer')}}</p>
                                </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Full Name')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('Bolin LIU')}}</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Email')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <a class="text-muted mb-0">scybl8@nottingham.edu.cn</a>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Phone')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">666666</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Address')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('The University of Nottingham, Ningbo, China')}}</p>
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>

                        <div class="container py-5">
                            <div class="row">
                            <div class="col-lg-4">
                                <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="/static/UserProfile/man1.png" alt="avatar"
                                    class="rounded-circle img-fluid" style="width: 110px;">
                                    <h5 class="my-3">{{$t('Weidong')}}</h5>
                                    <p class="text-muted mb-1">{{$t('Project Developer')}}</p>
                                    
                                </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Full Name')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('Weidong LU')}}</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Email')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">ssywl1@nottingham.edu.cn</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Phone')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">666666</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Address')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('The University of Nottingham, Ningbo, China')}}</p>
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>
                        
                        <div class="container py-5">
                            <div class="row">
                            <div class="col-lg-4">
                                <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="/static/UserProfile/women2.png" alt="avatar"
                                    class="rounded-circle img-fluid" style="width: 110px;">
                                    <h5 class="my-3">{{$t('Xinyi')}}</h5>
                                    <p class="text-muted mb-1">{{$t('Project Developer')}}</p>
                                    
                                </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Full Name')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('Xinyi WANG')}}</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Email')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">scyxw10@nottingham.edu.cn</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Phone')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">666666</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Address')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('The University of Nottingham, Ningbo, China')}}</p>
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>
                       
                        <div class="container py-5">
                            <div class="row">
                            <div class="col-lg-4">
                                <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="/static/UserProfile/women1.png" alt="avatar"
                                    class="rounded-circle img-fluid" style="width: 110px;">
                                    <h5 class="my-3">{{$t('Yiyang')}}</h5>
                                    <p class="text-muted mb-1">{{$t('Project Developer')}}</p> 
                                </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Full Name')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('Yiyang ZHANG')}}</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Email')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">scyyz15@nottingham.edu.cn</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Phone')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">666666</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Address')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('The University of Nottingham, Ningbo, China')}}</p>
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>

                        <div class="container py-5">
                            <div class="row">
                            <div class="col-lg-4">
                                <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img src="/static/UserProfile/man2.png" alt="avatar"
                                    class="rounded-circle img-fluid" style="width: 110px;">
                                    <h5 class="my-3">{{$t('Ziren')}}</h5>
                                    <p class="text-muted mb-1">{{$t('Project Developer')}}</p> 
                                </div>
                                </div>
                            </div>
                            <div class="col-lg-8">
                                <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Full Name')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('Ziren WANG')}}</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Email')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">ssyzw10@nottingham.edu.cn</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Phone')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">666666</p>
                                    </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                    <div class="col-sm-3">
                                        <p class="mb-0">{{$t('Address')}}</p>
                                    </div>
                                    <div class="col-sm-9">
                                        <p class="text-muted mb-0">{{$t('The University of Nottingham, Ningbo, China')}}</p>
                                    </div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</template>

<script>
export default {

}
</script>

<style>

</style>