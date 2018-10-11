package com.innoviti.emi.configuration;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.innoviti.emi.constant.JobType;
import com.innoviti.emi.database.csv.CSVSchemeModelTerminalModel;
import com.innoviti.emi.database.csv.DatabaseToCsvItemProcessor;
import com.innoviti.emi.database.csv.GeneralSchemeMapper;
import com.innoviti.emi.database.csv.GeneralSchemeProcessor;
import com.innoviti.emi.database.csv.GeneralSchemeReader;
import com.innoviti.emi.database.csv.HeaderWriter;
import com.innoviti.emi.entity.column.mapper.impl.SchemeModelColumnMapper;
import com.innoviti.emi.entity.column.mapper.impl.SchemeModelTerminalColumnMapper;
import com.innoviti.emi.entity.core.Category;
import com.innoviti.emi.entity.core.Manufacturer;
import com.innoviti.emi.entity.core.Model;
import com.innoviti.emi.entity.core.Product;
import com.innoviti.emi.entity.core.Scheme;
import com.innoviti.emi.entity.core.SchemeModel;
import com.innoviti.emi.entity.core.SchemeModelTerminal;
import com.innoviti.emi.entity.master.AssetCategoryMaster;
import com.innoviti.emi.entity.master.BranchMaster;
import com.innoviti.emi.entity.master.CityMaster;
import com.innoviti.emi.entity.master.DealerManufacturerMaster;
import com.innoviti.emi.entity.master.DealerMaster;
import com.innoviti.emi.entity.master.DealerProductMaster;
import com.innoviti.emi.entity.master.ManufacturerMaster;
import com.innoviti.emi.entity.master.ModelMaster;
import com.innoviti.emi.entity.master.ModelProductMaster;
import com.innoviti.emi.entity.master.SchemeBranchMaster;
import com.innoviti.emi.entity.master.SchemeDealerMaster;
import com.innoviti.emi.entity.master.SchemeMaster;
import com.innoviti.emi.entity.master.SchemeModelMaster;
import com.innoviti.emi.entity.master.StateMaster;
import com.innoviti.emi.exception.AlreadyMappedException;
import com.innoviti.emi.exception.NotFoundException;
import com.innoviti.emi.job.item.listener.FileItemSkipListener;
import com.innoviti.emi.job.item.listener.ItemSkipPolicy;
import com.innoviti.emi.job.item.listener.JobCompletionNotificationListener;
import com.innoviti.emi.job.item.listener.StepItemReadListener;
import com.innoviti.emi.job.item.processor.AssetCategoryItemProcessor;
import com.innoviti.emi.job.item.processor.GeneralSchemeModelItemProcessor;
import com.innoviti.emi.job.item.processor.ManufacturerItemProcessor;
import com.innoviti.emi.job.item.processor.ModelItemProcessor;
import com.innoviti.emi.job.item.processor.ProductItemProcessor;
import com.innoviti.emi.job.item.processor.SchemeItemProcessor;
import com.innoviti.emi.job.item.processor.SchemeModelItemProcessor;
import com.innoviti.emi.job.item.processor.SchemeModelTerminalItemProcessor;
import com.innoviti.emi.job.item.reader.JpaPagingPartitionerItemReader;
import com.innoviti.emi.job.item.reader.SchemeModelReader;
import com.innoviti.emi.job.item.reader.SchemeModelTerminalMappingReader;
import com.innoviti.emi.job.item.rowmapper.AssetCategoryMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.BranchMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.CityMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.DealerManufacturerMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.DealerMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.DealerProductMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.ManufacturerMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.ModelMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.ModelProductMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.SchemeBranchMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.SchemeDealerMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.SchemeMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.SchemeModelMasterRowMapper;
import com.innoviti.emi.job.item.rowmapper.StateMasterRowMapper;
import com.innoviti.emi.job.item.writer.AssetCategoryItemWriter;
import com.innoviti.emi.job.item.writer.AssetCategoryMasterWriter;
import com.innoviti.emi.job.item.writer.BranchMasterWriter;
import com.innoviti.emi.job.item.writer.CityMasterWriter;
import com.innoviti.emi.job.item.writer.DealerManufacturerWriter;
import com.innoviti.emi.job.item.writer.DealerMasterWriter;
import com.innoviti.emi.job.item.writer.DealerProductMasterWriter;
import com.innoviti.emi.job.item.writer.ManufacturerItemWriter;
import com.innoviti.emi.job.item.writer.ManufacturerMasterWriter;
import com.innoviti.emi.job.item.writer.ModelItemWriter;
import com.innoviti.emi.job.item.writer.ModelMasterWriter;
import com.innoviti.emi.job.item.writer.ModelProductMasterWriter;
import com.innoviti.emi.job.item.writer.ProductItemWriter;
import com.innoviti.emi.job.item.writer.SchemeBranchMasterWriter;
import com.innoviti.emi.job.item.writer.SchemeDealerWriter;
import com.innoviti.emi.job.item.writer.SchemeItemWriter;
import com.innoviti.emi.job.item.writer.SchemeMasterWriter;
import com.innoviti.emi.job.item.writer.SchemeModelItemWriter;
import com.innoviti.emi.job.item.writer.SchemeModelMasterWriter;
import com.innoviti.emi.job.item.writer.SchemeModelTerminalItemWriter;
import com.innoviti.emi.job.item.writer.StateMasterWriter;
import com.innoviti.emi.job.step.decider.StepExecutionDecider;
import com.innoviti.emi.job.step.spliter.ModelTerminalStepSplitter;
import com.innoviti.emi.job.step.spliter.StepSplitter;
import com.innoviti.emi.model.Supplier;
import com.innoviti.emi.util.Util;

@AutoConfigureAfter(value = {DataSourceConfiguration.class})
@Configuration
@EnableBatchProcessing
public class FileReaderjobConfiguration {

  private static final int CHUNK_SIZE = 1024;
  private static final String RESULT_FILE_TYPE_1 = Util.RESULT_FILE_TYPE_1;
  private static final String RESULT_FILE_TYPE_2 = Util.RESULT_FILE_TYPE_2;
  @Autowired
  private DataSource dataSource;

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private SchemeMasterWriter schemeMasterWriter;

  @Autowired
  private ManufacturerMasterWriter manufacturerMasterWriter;

  @Autowired
  private AssetCategoryMasterWriter assetCategoryMasterWriter;

  @Autowired
  private ModelMasterWriter modelMasterWriter;

  @Autowired
  private ModelProductMasterWriter modelProductMasterWriter;

  @Autowired
  private DealerMasterWriter dealerMasterWriter;

  @Autowired
  private CityMasterWriter cityMasterWriter;

  @Autowired
  private StateMasterWriter stateMasterWriter;

  @Autowired
  private BranchMasterWriter branchMasterWriter;

  @Autowired
  private DealerManufacturerWriter dealerManufacturerWriter;

  @Autowired
  private SchemeModelMasterWriter schemeModelMasterWriter;

  @Autowired
  private DealerProductMasterWriter dealerProductMasterWriter;

  @Autowired
  private SchemeDealerWriter schemeDealerWriter;

  @Autowired
  private SchemeBranchMasterWriter schemeBranchMasterWriter;

  // Core
  @Autowired
  private SchemeItemWriter schemeItemWriter;

  @Autowired
  private SchemeItemProcessor schemeItemProcessor;

  @Autowired
  private ManufacturerItemWriter manufacturerItemWriter;

  @Autowired
  private ManufacturerItemProcessor manufacturerItemProcessor;

  @Autowired
  private AssetCategoryItemWriter assetCategoryItemWriter;

  @Autowired
  private AssetCategoryItemProcessor assetCategoryItemProcessor;

  @Autowired
  private ModelItemWriter modelItemWriter;

  @Autowired
  private ModelItemProcessor modelItemProcessor;

  @Autowired
  private SchemeModelItemWriter schemeModelItemWriter;
  
  @Autowired
  private SchemeModelItemProcessor schemeModelItemProcessor;
  
  @Autowired
  private SchemeModelTerminalItemProcessor schemeModelTerminalItemProcessor;
  
  @Autowired
  private SchemeModelTerminalItemWriter schemeModelTerminalItemWriter; 
  
  @Autowired
  private JobCompletionNotificationListener jobCompletionNotificationListener;
  
  @Autowired
  private ProductItemProcessor productItemProcessor;
  
  @Autowired
  private ProductItemWriter productItemWriter;
  
  @Autowired
  private SchemeModelTerminalColumnMapper schemeModelTerminalColumnMapper;
  
  @Autowired
  private Supplier supplier;
  
  @Autowired
  private GeneralSchemeModelItemProcessor generalSchemeModelItemProcessor;
  
  @Autowired
  private SchemeModelColumnMapper schemeModelColumnMapper;

  @Bean
  public Job fileToDatabaseJob() throws Exception {
    return jobBuilderFactory.get(JobType.FILE_TO_DATABASE.getJobName())
        .listener(jobCompletionNotificationListener).start(createFileToDatabaseFlow()).next(jobHolderStep()).end()
        .build();
  }
  @Bean
  public Step jobHolderStep() throws Exception{
    return stepBuilderFactory.get("jobHolderStep").job(masterToCoreTableJob()).build();
     
  }
  @Bean
  public Flow createFileToDatabaseFlow() {
    SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("fileToDatabaseTaskExecutor");
    taskExecutor.setConcurrencyLimit(getAvailableProcessor());
    return new FlowBuilder<Flow>("fileToDatabaseParallerlFlow").split(taskExecutor)
        .add(schemeMasterFlow(null), manufacturerMasterFlow(null), assetCategoryMasterFlow(null),
            modelMasterFlow(null), modelProductMasterFlow(null), dealerMasterFlow(null), cityMasterFlow(null),
            stateMasterFlow(null), branchMasterFlow(null), dealerManufacturerMasterFlow(null),
            schemeModelMasterFlow(null), dealerProductMasterFlow(null), schemeDealerMasterFlow(null),
            schemeBranchMaterFlow(null)
            )
        .build();
  }

  // File to SchemeMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<SchemeMaster> schemeFileReader(
      @Value("#{jobParameters['SCHM']}") String filePath) {
    return reader(filePath, new SchemeMasterRowMapper());
  }

  private Step importSchemeMasterStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("importSchemeMasterStep").transactionManager(transactionManager)
        .<SchemeMaster, SchemeMaster>chunk(CHUNK_SIZE).reader(schemeFileReader(null))
        .writer(schemeMasterWriter).faultTolerant().skipPolicy(new ItemSkipPolicy())
        .listener(schemeMasterFileItemSkipListener(null))
        .stream(schemeMasterFileItemSkipListener(null)).build();
  }

  @Bean
  @StepScope
  public FileItemSkipListener<SchemeMaster, SchemeMaster> schemeMasterFileItemSkipListener(
      @Value("#{jobParameters['SCHM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }
  @Bean
  public Flow schemeMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("SCHM");
    return new FlowBuilder<Flow>("schemeMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(importSchemeMasterStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to SchemeMaster step

  // File to ManufacturerMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<ManufacturerMaster> manufacturerFileReader(
      @Value("#{jobParameters['MNFM']}") String filePath) {
    return reader(filePath, new ManufacturerMasterRowMapper());
  }

  @Bean
  public Flow manufacturerMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("MNFM");
    return new FlowBuilder<Flow>("manufacturerMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(manufacturerMasterStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }

  private Step manufacturerMasterStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("manufacturerStep").transactionManager(transactionManager)
        .<ManufacturerMaster, ManufacturerMaster>chunk(CHUNK_SIZE)
        .reader(manufacturerFileReader(null)).writer(manufacturerMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(manufacturerMasterFileItemSkipListener(null))
        .stream(manufacturerMasterFileItemSkipListener(null)).build();
  }

  @Bean
  @StepScope
  public FileItemSkipListener<ManufacturerMaster, ManufacturerMaster> manufacturerMasterFileItemSkipListener(
      @Value("#{jobParameters['MNFM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }
  // End file to ManufacturerMaster step

  // File to AssetCategoryMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<AssetCategoryMaster> assetCategoryFileReader(
      @Value("#{jobParameters['ASTM']}") String filePath) {
    return reader(filePath, new AssetCategoryMasterRowMapper());
  }

  private Step assetCategoryStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("assetCategoryStep").transactionManager(transactionManager)
        .<AssetCategoryMaster, AssetCategoryMaster>chunk(CHUNK_SIZE)
        .reader(assetCategoryFileReader(null)).writer(assetCategoryMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(assetCategoryMasterFileItemSkipListener(null))
        .stream(assetCategoryMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow assetCategoryMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("ASTM");
    return new FlowBuilder<Flow>("assetCategoryMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(assetCategoryStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }

  @Bean
  @StepScope
  public FileItemSkipListener<AssetCategoryMaster, AssetCategoryMaster> assetCategoryMasterFileItemSkipListener(
      @Value("#{jobParameters['ASTM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }
  // End file to AssetCategoryMaster step

  // File to ModelMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<ModelMaster> modelFileReader(
      @Value("#{jobParameters['MDLM']}") String filePath) {
    return reader(filePath, new ModelMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<ModelMaster, ModelMaster> modelMasterFileItemSkipListener(
      @Value("#{jobParameters['MDLM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step modelMasterStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("modelMasterStep").transactionManager(transactionManager).<ModelMaster, ModelMaster>chunk(CHUNK_SIZE)
        .reader(modelFileReader(null)).writer(modelMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(modelMasterFileItemSkipListener(null))
        .stream(modelMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow modelMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("MDLM");
    return new FlowBuilder<Flow>("modelMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(modelMasterStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to ModelMaster step

  // File to ModelProductMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<ModelProductMaster> modelProductMasterFileReader(
      @Value("#{jobParameters['MDPM']}") String filePath) {
    return reader(filePath, new ModelProductMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<ModelProductMaster, ModelProductMaster> modelProductMasterFileItemSkipListener(
      @Value("#{jobParameters['MDPM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step modelProductMasterStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("modelProductMasterStep").transactionManager(transactionManager)
        .<ModelProductMaster, ModelProductMaster>chunk(CHUNK_SIZE)
        .reader(modelProductMasterFileReader(null)).writer(modelProductMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(modelProductMasterFileItemSkipListener(null))
        .listener(modelProductMasterFileItemSkipListener(null))
        .stream(modelProductMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow modelProductMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("MDPM");
    return new FlowBuilder<Flow>("modelProductMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(modelProductMasterStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to ModelProductMaster step

  // File to DealerMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<DealerMaster> dealerMasterFileReader(
      @Value("#{jobParameters['DLRM']}") String filePath) {
    return reader(filePath, new DealerMasterRowMapper());

  }

  @Bean
  @StepScope
  public FileItemSkipListener<DealerMaster, DealerMaster> dealerMasterFileItemSkipListener(
      @Value("#{jobParameters['DLRM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }
  
  private Step dealerStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("dealerStep").transactionManager(transactionManager)
        .<DealerMaster, DealerMaster>chunk(CHUNK_SIZE)
        .reader(dealerMasterFileReader(null)).writer(dealerMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(dealerMasterFileItemSkipListener(null))
        .stream(dealerMasterFileItemSkipListener(null)).build();
  }

  @Bean
  @StepScope
  public StepItemReadListener<DealerMaster, DealerMaster> itemReadListner(@Value("#{jobParameters['DLRM']}") String filePath){
    StepItemReadListener<DealerMaster, DealerMaster> fileItem = new StepItemReadListener<>();
    fileItem.setFilePath(filePath, RESULT_FILE_TYPE_1);
    fileItem.setAppendAllowed(true);
    fileItem.setLineAggregator(item -> item);
    return fileItem;
  }
  
  @Bean
  public Flow dealerMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("DLRM");
    return new FlowBuilder<Flow>("dealerMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(dealerStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file DealerMaster step

  // File to CityMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<CityMaster> cityFileReader(
      @Value("#{jobParameters['CITM']}") String filePath) {
    return reader(filePath, new CityMasterRowMapper());

  }

  @Bean
  @StepScope
  public FileItemSkipListener<CityMaster, CityMaster> cityMasterFileItemSkipListener(
      @Value("#{jobParameters['CITM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step cityStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("cityStep").transactionManager(transactionManager)
        .<CityMaster, CityMaster>chunk(CHUNK_SIZE)
        .reader(cityFileReader(null)).writer(cityMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(cityMasterFileItemSkipListener(null))
        .stream(cityMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow cityMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("CITM");
    return new FlowBuilder<Flow>("cityMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(cityStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to CityMaster step

  // File to StateMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<StateMaster> stateFileReader(
      @Value("#{jobParameters['STAM']}") String filePath) {
    return reader(filePath, new StateMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<StateMaster, StateMaster> stateMasterFileItemSkipListener(
      @Value("#{jobParameters['STAM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step stateStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("stateStep").transactionManager(transactionManager)
        .<StateMaster, StateMaster>chunk(CHUNK_SIZE)
        .reader(stateFileReader(null)).writer(stateMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(stateMasterFileItemSkipListener(null))
        .stream(stateMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow stateMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("STAM");
    return new FlowBuilder<Flow>("stateMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(stateStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to StateMaster step

  // File to BranchMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<BranchMaster> branchFileReader(
      @Value("#{jobParameters['BRNM']}") String filePath) {
    return reader(filePath, new BranchMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<BranchMaster, BranchMaster> branchMasterFileItemSkipListener(
      @Value("#{jobParameters['BRNM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step branchStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("branchStep").transactionManager(transactionManager)
        .<BranchMaster, BranchMaster>chunk(CHUNK_SIZE)
        .reader(branchFileReader(null)).writer(branchMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(branchMasterFileItemSkipListener(null))
        .stream(branchMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow branchMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("BRNM");
    return new FlowBuilder<Flow>("branchMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(branchStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to BranchMaster step

  // File to DealerManufacturerMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<DealerManufacturerMaster> dealerManufacturerFileReader(
      @Value("#{jobParameters['DMFM']}") String filePath) {
    return reader(filePath, new DealerManufacturerMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<DealerManufacturerMaster, DealerManufacturerMaster> dealerManufacturerMasterFileItemSkipListener(
      @Value("#{jobParameters['DMFM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step dealerManufacturerStep(PlatformTransactionManager stepTransactionManager) {
    return stepBuilderFactory.get("dealerManufacturerStep").transactionManager(stepTransactionManager)
        .<DealerManufacturerMaster, DealerManufacturerMaster>chunk(CHUNK_SIZE)
        .reader(dealerManufacturerFileReader(null)).writer(dealerManufacturerWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy())
        .listener(dealerManufacturerMasterFileItemSkipListener(null))
        .stream(dealerManufacturerMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow dealerManufacturerMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager stepTransactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("DMFM");
    return new FlowBuilder<Flow>("dealerManufacturerMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(dealerManufacturerStep(stepTransactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to DealerManufacturerMaster step

  // File to SchemeModelMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<SchemeModelMaster> schemeModelFileReader(
      @Value("#{jobParameters['SHMM']}") String filePath) {
    return reader(filePath, new SchemeModelMasterRowMapper());

  }

  @Bean
  @StepScope
  public FileItemSkipListener<SchemeModelMaster, SchemeModelMaster> schemeModelMasterFileItemSkipListener(
      @Value("#{jobParameters['SHMM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step schemModelMasterStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("schemModelMasterStep").transactionManager(transactionManager)
        .<SchemeModelMaster, SchemeModelMaster>chunk(CHUNK_SIZE).reader(schemeModelFileReader(null))
        .writer(schemeModelMasterWriter).faultTolerant().skipPolicy(new ItemSkipPolicy())
        .listener(schemeModelMasterFileItemSkipListener(null))
        .stream(schemeModelMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow schemeModelMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("SHMM");
    return new FlowBuilder<Flow>("schemModelMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(schemModelMasterStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End File to SchemeModelMaster step

  // File to DealerProductMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<DealerProductMaster> dealerProductFileReader(
      @Value("#{jobParameters['DPRM']}") String filePath) {
    return reader(filePath, new DealerProductMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<DealerProductMaster, DealerProductMaster> dealerProductMasterFileItemSkipListener(
      @Value("#{jobParameters['DPRM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step dealerProductStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("dealerProductStep").transactionManager(transactionManager)
        .<DealerProductMaster, DealerProductMaster>chunk(CHUNK_SIZE)
        .reader(dealerProductFileReader(null)).writer(dealerProductMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(dealerProductMasterFileItemSkipListener(null))
        .stream(dealerProductMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow dealerProductMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("DPRM");
    return new FlowBuilder<Flow>("dealerProductMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(dealerProductStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to DealerProductMaster step

  // File to SchemeDealerMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<SchemeDealerMaster> schemeDealerFileReader(
      @Value("#{jobParameters['SHDM']}") String filePath) {
    return reader(filePath, new SchemeDealerMasterRowMapper());

  }

  @Bean
  @StepScope
  public FileItemSkipListener<SchemeDealerMaster, SchemeDealerMaster> schemeDealerMasterFileItemSkipListener(
      @Value("#{jobParameters['SHDM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step schemeDealerStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("schemeDealerStep").transactionManager(transactionManager)
        .<SchemeDealerMaster, SchemeDealerMaster>chunk(CHUNK_SIZE)
        .reader(schemeDealerFileReader(null)).writer(schemeDealerWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(schemeDealerMasterFileItemSkipListener(null))
        .stream(schemeDealerMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow schemeDealerMasterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("SHDM");
    return new FlowBuilder<Flow>("schemeDealerMasterFlow")
        .from(decider).on(StepExecutionDecider.START).to(schemeDealerStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End file to SchemeDealerMaster step

  // File to SchemeBranchMaster step
  @Bean
  @StepScope
  public FlatFileItemReader<SchemeBranchMaster> schemeBranchFileReader(
      @Value("#{jobParameters['SHBM']}") String filePath) {
    return reader(filePath, new SchemeBranchMasterRowMapper());
  }

  @Bean
  @StepScope
  public FileItemSkipListener<SchemeBranchMaster, SchemeBranchMaster> schemeBranchMasterFileItemSkipListener(
      @Value("#{jobParameters['SHBM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_1);
  }

  private Step schemeBranchStep(PlatformTransactionManager transactionManager) {
    return stepBuilderFactory.get("schemeBranchStep").transactionManager(transactionManager)
        .<SchemeBranchMaster, SchemeBranchMaster>chunk(CHUNK_SIZE)
        .reader(schemeBranchFileReader(null)).writer(schemeBranchMasterWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy()).listener(schemeBranchMasterFileItemSkipListener(null))
        .stream(schemeBranchMasterFileItemSkipListener(null)).build();
  }

  @Bean
  public Flow schemeBranchMaterFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) {
    JobExecutionDecider decider = new StepExecutionDecider("SHBM");
    return new FlowBuilder<Flow>("schemeBranchMaterFlow")
        .from(decider).on(StepExecutionDecider.START).to(schemeBranchStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }

  private <T> FlatFileItemReader<T> reader(String filePath, FieldSetMapper<T> fieldSetMapper) {
    FlatFileItemReader<T> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setLinesToSkip(1);
    DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
    lineMapper.setLineTokenizer(new DelimitedLineTokenizer("|"));
    lineMapper.setFieldSetMapper(fieldSetMapper);
    flatFileItemReader.setLineMapper(lineMapper);
    flatFileItemReader.setResource(new FileSystemResource(filePath));
    return flatFileItemReader;
  }

  // Master to core table job
  @Bean
  public Job masterToCoreTableJob() throws Exception {
    return jobBuilderFactory.get(JobType.MASTER_TO_CORE_TABLE.getJobName())
        .start(createMasterToCoreTableFlow())
        .next(modelMasterToModelFlow()).next(generalSchemModelMasterToSchemeModelFlow()).next(schemModelMasterToSchemeModelFlow())
        .next(schemeModelTerminalMappingFlow()).end()
        .build();
  }

  @Bean
  public Flow createMasterToCoreTableFlow() throws Exception {
    SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor("masterToCoreTableTaskExecutor");
    taskExecutor.setConcurrencyLimit(4);
    return new FlowBuilder<Flow>("createMasterToCoreTableFlow")
        .split(taskExecutor).add(schemeMasterToSchemeFlow(null), manufacturerMasterToManufacturerFlow(null), assetCategoryMasterToCategoryFlow(null),
            modelProductMasterToProductFlow(null))
        .build();
  }

  @Bean(destroyMethod="")
  public JpaPagingItemReader<SchemeMaster> schemeMasterTableReader() throws Exception {
    String sqlQuery = "SELECT * FROM scheme_bfl c INNER JOIN "
        + "(SELECT schemeid, MAX(record_update_date) as maxdate FROM scheme_bfl "
        + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY schemeid) ct "
        + "ON c.schemeid = ct.schemeid AND c.record_update_date = ct.maxdate";
        
    JpaNativeQueryProvider<SchemeMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    JpaPagingItemReader<SchemeMaster> jpaPaginItemReader = databaseReader("");
    jpaPaginItemReader.setQueryProvider(queryProvider);
    return jpaPaginItemReader;
  }

  @Bean
  @StepScope
  public FileItemSkipListener<SchemeMaster, Scheme> schemeTableItemSkipListener(
      @Value("#{jobParameters['SCHM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }

  private Step schemeMasterToSchemeStep(PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("schemeMasterToSchemeStep").transactionManager(transactionManager)
        .<SchemeMaster, Scheme>chunk(CHUNK_SIZE).reader(schemeMasterTableReader())
        .processor(schemeItemProcessor).writer(schemeItemWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(schemeTableItemSkipListener(null))
        .stream(schemeTableItemSkipListener(null)).build();
  }

  @Bean
  public Flow schemeMasterToSchemeFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    JobExecutionDecider decider = new StepExecutionDecider("SCHM");
    return new FlowBuilder<Flow>("schemeMasterToSchemeFlow")
        .from(decider).on(StepExecutionDecider.START).to(schemeMasterToSchemeStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }

  private <T> JpaPagingItemReader<T> databaseReader(String query) throws Exception {
    JpaPagingItemReader<T> jpaPagingItemReader = new JpaPagingItemReader<>();
    jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
    jpaPagingItemReader.setPageSize(CHUNK_SIZE);
    jpaPagingItemReader.setQueryString(query);
    jpaPagingItemReader.setSaveState(false);
    return jpaPagingItemReader;
  }
  private <T> JpaPagingPartitionerItemReader<T> databaseTableSplitReader(String query, Map<String, Object> parameterValues) throws Exception {
    JpaPagingPartitionerItemReader<T> jpaPagingItemReader = new JpaPagingPartitionerItemReader<>();
    jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
    jpaPagingItemReader.setPageSize(CHUNK_SIZE);
    jpaPagingItemReader.setQueryString(query);
    jpaPagingItemReader.setGridSize((int)parameterValues.get("gridSize"));
    jpaPagingItemReader.setChunkStartIndex((int)parameterValues.get("fromId"));
    jpaPagingItemReader.setSaveState(false);
    return jpaPagingItemReader;
  }
  // End SchemeMaster to Scheme table

  // ManufacturerMaster to Manufacturer table
  @Bean(destroyMethod="")
  public ItemReader<ManufacturerMaster> manufacturerMasterTableReader() throws Exception {
    String sqlQuery = "SELECT * FROM manufacturer_bfl c INNER JOIN "
        + "(SELECT manufacture_id, MAX(record_update_date) as maxdate FROM manufacturer_bfl "
        + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY manufacture_id) ct "
        + "ON c.manufacture_id = ct.manufacture_id AND c.record_update_date = ct.maxdate";
        
    JpaNativeQueryProvider<ManufacturerMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(ManufacturerMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    JpaPagingItemReader<ManufacturerMaster> jpaPaginItemReader = databaseReader("");
    jpaPaginItemReader.setQueryProvider(queryProvider);
    return jpaPaginItemReader;
  }

  @Bean
  @StepScope
  public FileItemSkipListener<ManufacturerMaster, Manufacturer> manufacturerTableItemSkipListener(
      @Value("#{jobParameters['MNFM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }

  private Step manufacturerMasterToManufacturerStep(PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("manufacturerMasterToManufacturerStep").transactionManager(transactionManager)
        .<ManufacturerMaster, Manufacturer>chunk(CHUNK_SIZE).reader(manufacturerMasterTableReader())
        .processor(manufacturerItemProcessor).writer(manufacturerItemWriter).faultTolerant()
        .skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(manufacturerTableItemSkipListener(null))
        .stream(manufacturerTableItemSkipListener(null)).build();
  }

  @Bean
  public Flow manufacturerMasterToManufacturerFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    JobExecutionDecider decider = new StepExecutionDecider("MNFM");
    return new FlowBuilder<Flow>("manufacturerMasterToManufacturerFlow")
        .from(decider).on(StepExecutionDecider.START).to(manufacturerMasterToManufacturerStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  // End ManufacturerMaster to Manufacturer table

  // AssetCategoryMaster to Category table
  @Bean(destroyMethod="")
  public ItemReader<AssetCategoryMaster> assetCategoryMasterTableReader() throws Exception {
    String sqlQuery = "SELECT * FROM asset_category_bfl c INNER JOIN "
        + "(SELECT catgid, MAX(record_update_date) as maxdate FROM asset_category_bfl "
        + "WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY catgid) ct "
        + "ON c.catgid = ct.catgid AND c.record_update_date = ct.maxdate";
    JpaNativeQueryProvider<AssetCategoryMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(AssetCategoryMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    JpaPagingItemReader<AssetCategoryMaster> jpaPaginItemReader = databaseReader("");
    jpaPaginItemReader.setQueryProvider(queryProvider);
    return jpaPaginItemReader;
  }

  @Bean
  @StepScope
  public FileItemSkipListener<AssetCategoryMaster, Category> categoryTableItemSkipListener(
      @Value("#{jobParameters['ASTM']}") String filePath) {
    return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }

  private Step assetCategoryMasterToCategoryStep(PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("assetCategoryMasterToCategoryStep").transactionManager(transactionManager)
        .<AssetCategoryMaster,Category>chunk(CHUNK_SIZE)
        .reader(assetCategoryMasterTableReader()).processor(assetCategoryItemProcessor)
        .writer(assetCategoryItemWriter).faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(categoryTableItemSkipListener(null)).stream(categoryTableItemSkipListener(null))
        .build();
  }

  @Bean
  public Flow assetCategoryMasterToCategoryFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    JobExecutionDecider decider = new StepExecutionDecider("ASTM");
    return new FlowBuilder<Flow>("assetCategoryMasterToCategoryFlow")
        .from(decider).on(StepExecutionDecider.START).to(assetCategoryMasterToCategoryStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  //End AssetCategoryMaster to Category table
  
  //ModelMaster to Model table
  @Bean(destroyMethod="")
  @StepScope
  public JpaPagingPartitionerItemReader<ModelMaster> modelMasterTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize) throws Exception {
    String sqlQuery = "SELECT * FROM model_master_bfl c INNER JOIN "
        +"(SELECT modelid, MAX(record_update_date) as maxdate FROM model_master_bfl "
        +"WHERE record_update_status = 'N' OR record_update_status = 'U' GROUP BY modelid) ct "
        +"ON c.modelid = ct.modelid AND c.record_update_date = ct.maxdate"; 
    
    JpaNativeQueryProvider<ModelMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(ModelMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("fromId", fromId);
    parameterValues.put("gridSize", gridSize);
    JpaPagingPartitionerItemReader<ModelMaster> jpaPagingItemReader = databaseTableSplitReader("", parameterValues);
    jpaPagingItemReader.setQueryProvider(queryProvider);
    return jpaPagingItemReader;
  }

  @Bean
  @StepScope
  public FileItemSkipListener<ModelMaster, Model> modelTableItemSkipListener(
      @Value("#{jobParameters['MDLM']}") String filePath) {
   return fileItemSkipListner(filePath,RESULT_FILE_TYPE_2);
  }
 
  @Bean
  public Step modelMasterToModelSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    return stepBuilderFactory.get("modelMasterToModelSplitterStep")
        .partitioner(modelMasterToModelStep(null))
        .partitioner("modelStepSplitter", stepSplitter())
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
 
  @Bean
  public Step modelMasterToModelStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("modelMasterToModelStep").transactionManager(transactionManager)
        .<ModelMaster, Model>chunk(CHUNK_SIZE)
        .reader(modelMasterTableReader(null, null)).processor(modelItemProcessor).writer(modelItemWriter)
        .faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(modelTableItemSkipListener(null)).stream(modelTableItemSkipListener(null))
        .build();
  }
  @Bean
  public Flow modelMasterToModelFlow() throws Exception{
    JobExecutionDecider decider = new StepExecutionDecider("MDLM");
    return new FlowBuilder<Flow>("modelMasterToModelFlow")
        .from(decider).on(StepExecutionDecider.START).to(modelMasterToModelSplitterStep())
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  //End ModelMaster to Model table
  
  //ModelProduct to Product table
  @Bean(destroyMethod="")
  public ItemReader<String> modelProductMasterTableReader() throws Exception {
    String sqlQuery = "SELECT DISTINCT(s.modelProductMasterComposite.code) FROM ModelProductMaster s";
    return databaseReader(sqlQuery);
  }
  @Bean
  @StepScope
  public FileItemSkipListener<ModelProductMaster, Product> modelProductMasterTableItemSkipListener(
      @Value("#{jobParameters['MDPM']}") String filePath) {
   return fileItemSkipListner(filePath,RESULT_FILE_TYPE_2);
  }
  private Step modelProductMasterToProductStep(PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("modelProductMasterToProductStep").transactionManager(transactionManager)
        .<String, Product>chunk(CHUNK_SIZE)
        .reader(modelProductMasterTableReader()).processor(productItemProcessor).writer(productItemWriter)
        .faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(modelProductMasterTableItemSkipListener(null))
        .stream(modelProductMasterTableItemSkipListener(null)).build();
  }
  @Bean
  public Flow  modelProductMasterToProductFlow(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception{
    JobExecutionDecider decider = new StepExecutionDecider("MDPM");
    return new FlowBuilder<Flow>("modelProductMasterToProductFlow")
        .from(decider).on(StepExecutionDecider.START).to(modelProductMasterToProductStep(transactionManager))
        .from(decider).on(StepExecutionDecider.SKIP).end(StepExecutionDecider.SKIP_COMPLETED)
        .build();
  }
  //End ModelProduct to Product table
  
  //SchemeModelMaster to SchemeModel
  @Bean(destroyMethod="")
  @StepScope
  public SchemeModelReader schemeModelMasterTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize) throws Exception {
    String sqlQuery = "SELECT * FROM scheme_bfl s INNER JOIN " 
        + "(SELECT schemeid, MAX(record_update_date) AS maxdate FROM scheme_bfl "
        + "WHERE (gen_sch ='N') AND (record_update_status = 'N' OR record_update_status = 'U') "
        +  "GROUP BY schemeid) ism "
        + "ON s.schemeid = ism.schemeid AND s.record_update_date = ism.maxdate";
    
    JpaPagingPartitionerItemReader<SchemeMaster> jpaPagingPartitionerItemReader = new JpaPagingPartitionerItemReader<>();
    JpaNativeQueryProvider<SchemeMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    jpaPagingPartitionerItemReader.setQueryProvider(queryProvider);
    SchemeModelReader schemeModelReader = new SchemeModelReader(jpaPagingPartitionerItemReader);
    schemeModelReader.setSchemeModelColumnMapper(schemeModelColumnMapper);
    schemeModelReader.setEntityManagerFactory(entityManagerFactory);
    schemeModelReader.setPageSize(CHUNK_SIZE);
    schemeModelReader.setQueryString(sqlQuery);
    schemeModelReader.setGridSize(gridSize.intValue());
    schemeModelReader.setChunkStartIndex(fromId.intValue());
    return schemeModelReader;
  }
  @Bean
  @StepScope
  public FileItemSkipListener<SchemeModel, SchemeModel> schemeModelTableItemSkipListener(
      @Value("#{jobParameters['SHMM']}") String filePath) {
   return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }
  @Bean
  public Step schemModelMasterToSchemeModelSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    StepSplitter stepSplitter = stepSplitter();
    stepSplitter.setChunkSize(CHUNK_SIZE);
    return stepBuilderFactory.get("schemModelMasterToSchemeModelStep")
        .partitioner(schemModelMasterToSchemeModelStep(null))
        .partitioner("schemeModelStepSplitter", stepSplitter)
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
  @Bean
  public Step schemModelMasterToSchemeModelStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("schemModelMasterToSchemeModelStep").transactionManager(transactionManager)
        .<SchemeModel, SchemeModel>chunk(CHUNK_SIZE).reader(schemeModelMasterTableReader(null, null))
        .writer(schemeModelItemWriter).processor(schemeModelItemProcessor).faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRollback(AlreadyMappedException.class)
        .noRetry(NotFoundException.class).noRetry(AlreadyMappedException.class)
        .skip(NotFoundException.class).skip(AlreadyMappedException.class)
        .listener(schemeModelTableItemSkipListener(null)).stream(schemeModelTableItemSkipListener(null))
        .build();
  }
  @Bean
  public Flow schemModelMasterToSchemeModelFlow() throws Exception{
    return new FlowBuilder<Flow>("schemModelMasterToSchemeModelFlow")
        .start(schemModelMasterToSchemeModelSplitterStep())
        .build();
  }
  //End SchemeModelMaster to SchemeModel
  
//Model to  General Scheme mapping
//  @Bean(destroyMethod="")
//  @StepScope
//  public GeneralSchemeModelMappingReader generalSchemeModelMasterTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
//      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize) throws Exception {
//    String sqlQuery = "SELECT s FROM Model s WHERE s.modelComposite.innoModelCode='XXXXXXXXXXGEN'";
//    JpaPagingPartitionerItemReader<Model> jpaPagingPartitionerItemReader = new JpaPagingPartitionerItemReader<>();
//    GeneralSchemeModelMappingReader jpaPagingItemReader = new GeneralSchemeModelMappingReader(jpaPagingPartitionerItemReader);
//    jpaPagingItemReader.setGeneralSchemeModelColumnMapper(generalSchemeModelColumnMapper);
//    jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
//    jpaPagingItemReader.setPageSize(10);
//    jpaPagingItemReader.setQueryString(sqlQuery);
//    jpaPagingItemReader.setGridSize(gridSize.intValue());
//    jpaPagingItemReader.setChunkStartIndex(fromId.intValue());
//    return jpaPagingItemReader;
//  }
  @Bean
  @StepScope
  public JpaPagingPartitionerItemReader<SchemeMaster> generalSchemeModelMasterTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize){
    String sqlQuery = "SELECT * FROM scheme_bfl s INNER JOIN " 
        + "(SELECT schemeid, MAX(record_update_date) AS maxdate FROM scheme_bfl "
        + "WHERE (gen_sch ='Y') "
        + "OR (gen_sch='N' AND dealer_mapping = 'N' AND model_mapping = 'N') "
        +  "GROUP BY schemeid) ism "
        + "ON s.schemeid = ism.schemeid AND s.record_update_date = ism.maxdate";
    
    JpaNativeQueryProvider<SchemeMaster> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeMaster.class);
    queryProvider.setSqlQuery(sqlQuery);
    JpaPagingPartitionerItemReader<SchemeMaster> jpaPagingItemReader = new JpaPagingPartitionerItemReader<>();
    jpaPagingItemReader.setEntityManagerFactory(entityManagerFactory);
    jpaPagingItemReader.setPageSize(CHUNK_SIZE);
    jpaPagingItemReader.setGridSize(gridSize.intValue());
    jpaPagingItemReader.setChunkStartIndex(fromId.intValue());
    jpaPagingItemReader.setQueryProvider(queryProvider);
    
    return jpaPagingItemReader;
  }
  @Bean
  @StepScope
  public FileItemSkipListener<SchemeMaster, SchemeModel> generalSchemeModelTableItemSkipListener(
      @Value("#{jobParameters['SHMM']}") String filePath) {
   return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }
  @Bean
  public StepSplitter generalSchemeStepSplitter(){
    StepSplitter stepSplitter = new StepSplitter();
    stepSplitter.setChunkSize(CHUNK_SIZE);
    return stepSplitter;
  }
  @Bean
  public Step generalSchemModelMasterToSchemeModelSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    return stepBuilderFactory.get("generalSchemModelMasterToSchemeModelSplitterStep")
        .partitioner(generalSchemModelMasterToSchemeModelStep(null))
        .partitioner("generalSchemeModelStepSplitter", generalSchemeStepSplitter())
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
  @Bean
  public Step generalSchemModelMasterToSchemeModelStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception {
    return stepBuilderFactory.get("generalSschemModelMasterToSchemeModelStep").transactionManager(transactionManager)
        .<SchemeMaster, SchemeModel>chunk(CHUNK_SIZE).reader(generalSchemeModelMasterTableReader(null, null))
        .writer(schemeModelItemWriter).processor(generalSchemeModelItemProcessor)
        .faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRollback(AlreadyMappedException.class)
        .noRetry(NotFoundException.class).noRetry(AlreadyMappedException.class)
        .skip(NotFoundException.class).skip(AlreadyMappedException.class)
        .listener(generalSchemeModelTableItemSkipListener(null)).stream(generalSchemeModelTableItemSkipListener(null))
        .build();
  }
  @Bean
  public Flow generalSchemModelMasterToSchemeModelFlow() throws Exception{
    return new FlowBuilder<Flow>("generalSchemModelMasterToSchemeModelFlow")
        .start(generalSchemModelMasterToSchemeModelSplitterStep())
        .build();
  }
  //End SchemeModelMaster to SchemeModel
  
  //SchemeModelMapping to Scheme model terminal mapping
  public JpaPagingPartitionerItemReader<SchemeModel> schemeModelTableReader(Integer fromId, Integer gridSize) throws Exception {
    String sqlQuery = "SELECT * FROM issuer_scheme_model i INNER JOIN "
        + "(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model  "
        + "WHERE record_update_status = 'N' OR record_update_status = 'U' "
        + "GROUP BY issuer_scheme_model_code) ism "
        + "ON i.issuer_scheme_model_code = ism.issuer_scheme_model_code AND i.record_update_timestamp = ism.maxdate ";
    
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("fromId", fromId);
    parameterValues.put("gridSize", gridSize);
    JpaNativeQueryProvider<SchemeModel> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeModel.class);
    queryProvider.setSqlQuery(sqlQuery);
    
    JpaPagingPartitionerItemReader<SchemeModel> jpaPagingItemReader = databaseTableSplitReader("", parameterValues);
    jpaPagingItemReader.setQueryProvider(queryProvider);
    return jpaPagingItemReader;
  }

  @Bean(destroyMethod="")
  @StepScope
  public SchemeModelTerminalMappingReader schemeModelMappingReader() throws Exception{
    SchemeModelTerminalMappingReader schemeModelMappingReader = new SchemeModelTerminalMappingReader();
    schemeModelMappingReader.setSupplier(supplier);
    schemeModelMappingReader.setSchemeModelTerminalMapper(schemeModelTerminalColumnMapper);
    return schemeModelMappingReader;
  }
  @Bean
  @StepScope
  public FileItemSkipListener<SchemeModelTerminal, SchemeModelTerminal> schemeModelTerminalMappingItemSkipListener(
      @Value("#{jobParameters['SMTM']}") String filePath) {
  
   return fileItemSkipListner(filePath, RESULT_FILE_TYPE_2);
  }
  
 
  @Bean
  public ModelTerminalStepSplitter modelTerminalStepSplitter(){
    ModelTerminalStepSplitter modelTerminalStepSplitter = new ModelTerminalStepSplitter();
    modelTerminalStepSplitter.setChunkSize(CHUNK_SIZE);
    return modelTerminalStepSplitter;
  }
  @Bean
  public Step schemeModelTerminalMappingSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    ModelTerminalStepSplitter modelTerminalStepSplitter = modelTerminalStepSplitter();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    return stepBuilderFactory.get("schemeModelTerminalMappingSplitterStep")
        .partitioner(schemeModelTerminalMappingStep(null))
        .partitioner("schemeModelTerminalStepSplitter", modelTerminalStepSplitter)
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
  @Bean
  public Step schemeModelTerminalMappingStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception{
    return stepBuilderFactory.get("schemeModelTerminalMappingStep").transactionManager(transactionManager)
        .<SchemeModelTerminal, SchemeModelTerminal>chunk(CHUNK_SIZE)
        .reader(schemeModelMappingReader()).processor(schemeModelTerminalItemProcessor).writer(schemeModelTerminalItemWriter)
        .faultTolerant().skipPolicy(new ItemSkipPolicy())
        .noRollback(NotFoundException.class).noRetry(NotFoundException.class).skip(NotFoundException.class)
        .listener(schemeModelTerminalMappingItemSkipListener(null))
        .stream( schemeModelTerminalMappingItemSkipListener(null)).build();
  }
  @Bean
  public Flow schemeModelTerminalMappingFlow() throws Exception{
    return new FlowBuilder<Flow>("schemeModelTerminalMappingFlow")
        .start(schemeModelTerminalMappingSplitterStep())
        .build();
  }
  //End SchemeModelMapping to Scheme model terminal mapping
  
  private <T, S> FileItemSkipListener<T, S> fileItemSkipListner(String filePath, String fileType){
    FileItemSkipListener<T, S> fileItem = new FileItemSkipListener<>(filePath, fileType);
    fileItem.setAppendAllowed(true);
    fileItem.setLineAggregator(item -> item);
    return fileItem;
  }

 
  @Bean
  @Qualifier("stepTransactionManager")
  public PlatformTransactionManager stepTransactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory);
    return transactionManager;
  }
  
  @Bean
  public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
    jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
    return jobRegistryBeanPostProcessor;
  }

  @Bean
  public JobRepository jobRepository(
      @Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager)
      throws Exception {
    JobRepositoryFactoryBean jobRepoBean = new JobRepositoryFactoryBean();
    jobRepoBean.setDataSource(dataSource);
    jobRepoBean.setTransactionManager(transactionManager);
    return jobRepoBean.getObject();
  }

  @Bean
  public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
    SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
    simpleJobLauncher.setJobRepository(jobRepository);
    SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
    asyncTaskExecutor.setConcurrencyLimit(1);
    simpleJobLauncher.setTaskExecutor(asyncTaskExecutor);
    return simpleJobLauncher;
  }
  @Bean
  public StepSplitter stepSplitter(){
   StepSplitter stepSplitter = new StepSplitter();
   stepSplitter.setChunkSize(CHUNK_SIZE);
   return stepSplitter;
  }
  @Bean
  public int getAvailableProcessor(){
    return Runtime.getRuntime().availableProcessors();
  }
  
  
  //Database to csv
  @Autowired
  private GeneralSchemeMapper generalSchemeMapper;
  
  @Autowired
  private GeneralSchemeProcessor generalSchemeProcessor;
  
  @Autowired
  private DatabaseToCsvItemProcessor databaseToCsvItemProcessor;
  
  @Bean(destroyMethod = "")
  @StepScope
  public GeneralSchemeReader generalSchemeTerminalTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize, @Value("#{jobParameters['utid']}") String utid) throws Exception {
    String sqlQuery = "SELECT * FROM issuer_scheme_model_terminal s INNER JOIN "
        +"(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model_terminal "
        +"WHERE (record_update_status ='N' OR record_update_status ='U') "
        +"GROUP BY issuer_scheme_model_code) ism " 
        +"ON s.issuer_scheme_model_code = ism.issuer_scheme_model_code " 
        +"AND s.record_update_timestamp = ism.maxdate WHERE  utid = '"+utid+"'";
    
    JpaPagingPartitionerItemReader<SchemeModelTerminal> jpaPagingPartitionerItemReader = new JpaPagingPartitionerItemReader<>();
    JpaNativeQueryProvider<SchemeModelTerminal> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeModelTerminal.class);
    queryProvider.setSqlQuery(sqlQuery);
    jpaPagingPartitionerItemReader.setQueryProvider(queryProvider);
    GeneralSchemeReader generalSchemeReader = new GeneralSchemeReader(jpaPagingPartitionerItemReader);
    generalSchemeReader.setGeneralSchemeMapper(generalSchemeMapper);
    generalSchemeReader.setEntityManagerFactory(entityManagerFactory);
    generalSchemeReader.setPageSize(CHUNK_SIZE);
    generalSchemeReader.setQueryString("");
    generalSchemeReader.setGridSize(gridSize.intValue());
    generalSchemeReader.setChunkStartIndex(fromId.intValue());
    return generalSchemeReader;
    
  }
  @Bean
  public Step generalSchemeCsvStepSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    StepSplitter stepSplitter = databasetoCsvStepSplitter();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    return stepBuilderFactory.get("generalSchemeCsvStepSplitterStep")
        .partitioner(generalSchemeCsvStep(null))
        .partitioner("generalSchemeStepSplitter", stepSplitter)
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
  @Bean
  public Flow generalSchemeCsvFlow() throws Exception{
    return new FlowBuilder<Flow>("generalSchemeCsvFlow").start(generalSchemeCsvStepSplitterStep())
        .build();
  }
  @Bean
  public Step generalSchemeCsvStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception{
    return stepBuilderFactory.get("generalSchemeCsvStep").transactionManager(transactionManager)
        .<CSVSchemeModelTerminalModel, CSVSchemeModelTerminalModel>chunk(CHUNK_SIZE)
        .reader(generalSchemeTerminalTableReader(null, null, null)).writer(databaseCsvItemWriter(null, null , null)).processor(generalSchemeProcessor)
        .faultTolerant().skipPolicy(new ItemSkipPolicy()).build();
  }
  @Bean(destroyMethod= "")
  @StepScope
  public JpaPagingPartitionerItemReader<SchemeModelTerminal> schemeModelTerminalTableReader(@Value("#{stepExecutionContext['fromId']}") Integer fromId,
      @Value("#{stepExecutionContext['gridSize']}")  Integer gridSize, @Value("#{jobParameters['utid']}") String utid) throws Exception {
    String sqlQuery = "SELECT * FROM issuer_scheme_model_terminal s INNER JOIN "
        +"(SELECT issuer_scheme_model_code, MAX(record_update_timestamp) AS maxdate FROM issuer_scheme_model_terminal "
        +"WHERE (record_update_status ='N' OR record_update_status ='U') "
        +"GROUP BY issuer_scheme_model_code) ism " 
        +"ON s.issuer_scheme_model_code = ism.issuer_scheme_model_code " 
        +"AND s.record_update_timestamp = ism.maxdate WHERE utid = '"+utid+"'";
    
    Map<String, Object> parameterValues = new HashMap<>();
    parameterValues.put("fromId", fromId);
    parameterValues.put("gridSize", gridSize);
    
    JpaNativeQueryProvider<SchemeModelTerminal> queryProvider = new JpaNativeQueryProvider<>();
    queryProvider.setEntityClass(SchemeModelTerminal.class);
    queryProvider.setSqlQuery(sqlQuery);
    JpaPagingPartitionerItemReader<SchemeModelTerminal> jpaPagingItemReader = databaseTableSplitReader("", parameterValues);
    jpaPagingItemReader.setQueryProvider(queryProvider);
    return jpaPagingItemReader;
  }
  
  @Bean
  public StepSplitter databasetoCsvStepSplitter(){
    StepSplitter stepSplitter = new StepSplitter();
    stepSplitter.setChunkSize(CHUNK_SIZE);
    return stepSplitter;
  }
  @Bean
  public Step databasetoCsvStepSplitterStep() throws Exception{
    SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
    int processor = getAvailableProcessor();
    StepSplitter stepSplitter = databasetoCsvStepSplitter();
    simpleAsyncTaskExecutor.setConcurrencyLimit(processor);
    return stepBuilderFactory.get("databasetoCsvStepSplitterStep")
        .partitioner(databasetoCsvStep(null))
        .partitioner("schemeModelTerminalStepSplitter", stepSplitter)
        .gridSize(processor).taskExecutor(simpleAsyncTaskExecutor).build();
  }
  

  @Bean(destroyMethod= "")
  @StepScope
  public FlatFileItemWriter<CSVSchemeModelTerminalModel> databaseCsvItemWriter(@Value("#{jobParameters['utid']}") String utid,
      @Value("#{jobParameters['folder_path']}") String folderPath, @Value("#{jobParameters['date']}") Date date) {
      FlatFileItemWriter<CSVSchemeModelTerminalModel> csvFileWriter = new FlatFileItemWriter<>();

      csvFileWriter.setHeaderCallback(headerWriter());
      StringBuilder filePathBuilder = new StringBuilder();
      String forrmattedDate = Util.formatDateToString("yyyyMMddHHmmss", date);
      filePathBuilder.append(folderPath).append(File.separator).append("emi_data_")
      .append(forrmattedDate).append("_").append(utid).append(".csv");
      csvFileWriter.setResource(new FileSystemResource(filePathBuilder.toString()));
      LineAggregator<CSVSchemeModelTerminalModel> lineAggregator = createCsvLineAggregator();
      csvFileWriter.setLineAggregator(lineAggregator);
      csvFileWriter.setAppendAllowed(true);
      return csvFileWriter;
  }
  @Bean
  public HeaderWriter headerWriter(){
    return new HeaderWriter("manufacturer_display_name,category_display_name,"
        + "model_display_name,model_code,scheme_display_name,general_scheme,emi_scheme_code,emi_bank_code,"
        + "emi_bank_display_name,Rate_of_Interest,advance_emi,min_amount,max_amount,scheme_start_date,scheme_end_date,scheme_model_code");
    
  }
  private LineAggregator<CSVSchemeModelTerminalModel> createCsvLineAggregator() {
    DelimitedLineAggregator<CSVSchemeModelTerminalModel> lineAggregator = new DelimitedLineAggregator<>();
    lineAggregator.setDelimiter(",");
    FieldExtractor<CSVSchemeModelTerminalModel> fieldExtractor = createSchemeFieldExtractor();
    lineAggregator.setFieldExtractor(fieldExtractor);

    return lineAggregator;
  }
  @Bean
  public Step databasetoCsvStep(@Qualifier("stepTransactionManager") PlatformTransactionManager transactionManager) throws Exception{
    return stepBuilderFactory.get("databasetoCsvStep").transactionManager(transactionManager)
        .<SchemeModelTerminal, CSVSchemeModelTerminalModel>chunk(CHUNK_SIZE)
        .reader(schemeModelTerminalTableReader(null, null, null)).writer(databaseCsvItemWriter(null, null, null)).processor(databaseToCsvItemProcessor)
        .faultTolerant().skipPolicy(new ItemSkipPolicy()).build();
  }
  private FieldExtractor<CSVSchemeModelTerminalModel> createSchemeFieldExtractor() {
    BeanWrapperFieldExtractor<CSVSchemeModelTerminalModel> extractor = new BeanWrapperFieldExtractor<>();
    extractor.setNames(new String[] {"manufacturerDisplayName", "categoryDisplayName", "modelDisplayNo",
        "innoModelCode","schemeDisplayName", "genScheme","innoIssuerSchemeCode", "emiBankCode","issuerBankDisplayName", "roi",
        "advanceEmi", "minAmount", "maxAmount", "schemeStartDate", "schemeEndDate", "innoSchemeModelCode"});
    return extractor;
  }
  @Bean
  public Flow databasetoCsvFlow() throws Exception{
    return new FlowBuilder<Flow>("databasetoCsvFlow").start(databasetoCsvStepSplitterStep())
        .build();
  }
  @Bean("databaseToCsvJob")
  public Job databaseToCsvJob() throws Exception {
    return jobBuilderFactory.get(JobType.DATABASE_TO_CSV.getJobName()).incrementer(new RunIdIncrementer())
        .start(generalSchemeCsvFlow())
        .next(databasetoCsvFlow()).end()
        .build();
  }
  
}
