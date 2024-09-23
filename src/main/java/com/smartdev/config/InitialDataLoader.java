package com.smartdev.config;

import com.smartdev.entity.ParametersEntity;
import com.smartdev.entity.ScheduledTaskEntity;
import com.smartdev.repository.ParameterRepository;
import com.smartdev.repository.ScheduledTaskRepository;
import com.smartdev.services.DynamicScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Classe para cadastrar dados iniciais
 */
@Configuration
public class InitialDataLoader {

    private static final Logger logger = Logger.getLogger(InitialDataLoader.class.getName());

    @Autowired
    private ScheduledTaskRepository scheduledTaskRepository;

    @Autowired
    private DynamicScheduledTaskService dynamicScheduledTaskService;

    @Autowired
    private ParameterRepository parameterRepository;

    @Bean
    public CommandLineRunner loadData() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                InitialDataLoader.this.createScheduleTaskEntity();
                InitialDataLoader.this.createParametersEntityData();
            }

        };
    }

    private void createScheduleTaskEntity() throws Exception {
        List<ScheduledTaskEntity> scheduledTasks = new ArrayList<ScheduledTaskEntity>();

        // Criar e salvar tarefas iniciais
        scheduledTasks.add(new ScheduledTaskEntity(null, "VolumetryDeleteRowsBigqueryService", "0 0 1 * * ?", "Executa todos os dias à 1 hora da manhã", true));
        scheduledTasks.add(new ScheduledTaskEntity(null, "DataMonitoringService", "0 20 21 * * ?",
                "Executa à meia-noite todos os dias e consulta a volumetria do source e do sink", true));
        scheduledTasks
                .add(new ScheduledTaskEntity(null, "NotificationService", "0 */10 * * * ?", "Envia notificações de 10 em 10 minutos caso seja necessário", true));

        for (ScheduledTaskEntity scheduledTask : scheduledTasks) {
            Optional<ScheduledTaskEntity> existingTask = scheduledTaskRepository.findByServiceName(scheduledTask.getServiceName());

            if (existingTask.isPresent() == false) {
                logger.info("Cadastrando a Task: " + scheduledTask.getServiceName());
                scheduledTaskRepository.save(scheduledTask); // Criar o cadastro

                logger.info("Agendando a Task no sistema: " + scheduledTask.getServiceName());
                dynamicScheduledTaskService.scheduleTask(scheduledTask); // Agendar as tarefas
            }
        }
    }

    public void createParametersEntityData() {
        List<ParametersEntity> initialData = Arrays.asList( //

                new ParametersEntity(null, "name", "Nome do Conector", "all", "", "Nome do connector"), //
                new ParametersEntity(null, "connector.class", "Classe do Conector", "all", "", "Nome da classe java do connector"), //
                new ParametersEntity(null, "defaultDataset", "Dataset Bigquery", "sink", "", "Nome do dataset que irá receber os dados no destino"), //
                new ParametersEntity(null, "projectBigquery", "Projeto Bigquery", "sink", "", "Nome do prrojeto que irá receber os dados no destino"), //
                new ParametersEntity(null, "mergeIntervalMs", "Merge Interval (ms)", "sink", "60.000",
                        "O intervalo de tempo em milissegundos entre operações de mesclagem, essa config sobrepõe todas as outras de limite"), //
                new ParametersEntity(null, "database.dbname", "Banco de Dados", "source", "", "Nome do banco de dados de origem"), //
                new ParametersEntity(null, "database.port", "Porta do banco", "source", "", "Numero da porta de conexão no banco de dados de origem"), //
                new ParametersEntity(null, "database.hostname", "Host/IP", "source", "", "Host ou IP de conexão no banco de dados de origem"), //
                new ParametersEntity(null, "database.user", "Usuario do Banco", "source", "", "Usuário de conexão no banco de dados de origem"), //
                new ParametersEntity(null, "database.password", "Senha", "source", "", "Senha de conexão no banco de dados de origem"), //
                new ParametersEntity(null, "table.include.list", "Lista Tabelas", "source", "", "Lista de tabelas na origem que o conector deve sincronizar."));

        for (ParametersEntity data : initialData) {
            Optional<ParametersEntity> existingRecord = parameterRepository.findByName(data.getName());

            if (existingRecord.isPresent()) {
                ParametersEntity recordToUpdate = existingRecord.get();
                recordToUpdate.setName(data.getName());
                recordToUpdate.setTitle(data.getTitle());
                recordToUpdate.setType(data.getType());
                recordToUpdate.setDefaultValue(data.getDefaultValue());
                recordToUpdate.setDescription(data.getDescription());

                parameterRepository.save(recordToUpdate);
            } else {
                parameterRepository.save(data);
            }
        }

    }
}
